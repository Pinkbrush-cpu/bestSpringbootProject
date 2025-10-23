package com.czy.template.service;

import com.czy.template.view.dto.PageRespDTO;
import com.czy.template.mapper.ClazzMapper;
import com.czy.template.pojo.Clazz;
import com.czy.template.view.vo.ClazzVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  ClazzService {

    @Autowired
    private final ClazzMapper clazzMapper;

    public PageRespDTO<ClazzVO> getClazzVOPage(Integer page, Integer size, String keyword) {
        long offset = (long) (page - 1) * size;

        /* ① 数据库分页查班级 */
        List<Clazz> clazzList = clazzMapper.selectClazzPage(offset, size, keyword);

        /* ② 组装 VO（逐条去查教师姓名 & 学生数） */
        List<ClazzVO> voList = clazzList.stream().map(c -> {
            ClazzVO v = new ClazzVO();
            v.setClassId(c.getClassId());
            v.setClassName(c.getClassName());
            v.setClassCode(c.getClassCode());
            v.setTeacherId(c.getTeacherId());
            v.setAdmissionYear(c.getAdmissionYear());
            v.setStudentMaxCount(c.getStudentMaxCount());
            v.setClassState(c.getClassState());

            /* ②-1 教师姓名（单条查询） */
            v.setTeacherName(clazzMapper.selectTeachers(c.getTeacherId()));

            /* ②-2 学生数（单条查询） */
            v.setStudentCount(clazzMapper.selectStuCount(c.getClassId().intValue()));

            return v;
        }).collect(Collectors.toList());

        /* ③ 总条数（需要额外一条 count） */
        int total = clazzMapper.countClazz(keyword);

        /* ④ 封装返回 */
        return PageRespDTO.<ClazzVO>builder()
                .list(voList)
                .total(total)
                .pages((total + size - 1) / size)
                .current(page)
                .size(size)
                .build();
    }
}