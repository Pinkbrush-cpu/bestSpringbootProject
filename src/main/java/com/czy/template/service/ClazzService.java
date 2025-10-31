package com.czy.template.service;

import com.czy.template.util.JwtUtil;
import com.czy.template.view.dto.PageRespDTO;
import com.czy.template.mapper.ClazzMapper;
import com.czy.template.pojo.Clazz;
import com.czy.template.view.vo.ClazzVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  ClazzService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private final ClazzMapper clazzMapper;

    public PageRespDTO<ClazzVO> getClazzVOPage(Integer page, Integer size, String keyword) {
        long offset = (long) (page - 1) * size;

        List<Clazz> clazzList = clazzMapper.selectClazzPage(offset, size, keyword);

        List<ClazzVO> voList = clazzList.stream().map(c -> {
            ClazzVO v = new ClazzVO();
            v.setClassId(c.getClassId());
            v.setClassName(c.getClassName());
            v.setClassCode(c.getClassCode());
            v.setTeacherId(c.getTeacherId());
            v.setAdmissionYear(c.getAdmissionYear());
            v.setStudentMaxCount(c.getStudentMaxCount());
            v.setClassState(c.getClassState());

            v.setTeacherName(clazzMapper.selectTeachers(c.getTeacherId()));

            v.setStudentCount(clazzMapper.selectStuCount(c.getClassId().intValue()));

            return v;
        }).collect(Collectors.toList());

        int total = clazzMapper.countClazz(null,keyword);

        return PageRespDTO.<ClazzVO>builder()
                .list(voList)
                .total(total)
                .pages((total + size - 1) / size)
                .current(page)
                .size(size)
                .build();
    }

    //教师端查看班级
    public PageRespDTO<ClazzVO> viewClass(Integer page, Integer size, String keyword, HttpServletRequest req) {
        Long teacherId = jwtUtil.getUserFromRequest(req).getId();

        int offset = (page - 1) * size;
        List<Clazz> clazzList = clazzMapper.selectClazzPageByTeacherId(offset, size, keyword,teacherId);


        List<ClazzVO> voList = clazzList.stream().map(c -> {
            ClazzVO v = new ClazzVO();
            v.setClassId(c.getClassId());
            v.setClassName(c.getClassName());
            v.setClassCode(c.getClassCode());
            v.setTeacherId(teacherId);
            v.setAdmissionYear(c.getAdmissionYear());
            v.setStudentMaxCount(c.getStudentMaxCount());
            v.setClassState(c.getClassState());

            v.setTeacherName(clazzMapper.selectTeachers(teacherId));

            v.setStudentCount(clazzMapper.selectStuCount(c.getClassId().intValue()));

            return v;
        }).collect(Collectors.toList());

        int total = clazzMapper.countClazz(teacherId,keyword);

        return PageRespDTO.<ClazzVO>builder()
                .list(voList)
                .total(total)
                .pages((total + size - 1) / size)
                .current(page)
                .size(size)
                .build();
    }

    //学生端查看班级
    public PageRespDTO<ClazzVO> studentViewClass(Integer page, Integer size, String keyword, HttpServletRequest req) {
        Long studentId = jwtUtil.getUserFromRequest(req).getId();

        int offset = (page - 1) * size;
        List<Long> clazzIds = clazzMapper.selectByStudentId(offset, size, keyword,studentId);

        List<Clazz> clazzList = new ArrayList<>();
        for (Long clazzId : clazzIds) {
            Clazz clazz = clazzMapper.selectClazzById(clazzId);
            if (clazz != null) {
                clazzList.add(clazz);
            }
        }
        List<ClazzVO> voList = clazzList.stream().map(c -> {
            ClazzVO v = new ClazzVO();
            v.setClassId(c.getClassId());
            v.setClassName(c.getClassName());
            v.setClassCode("000000");
            v.setTeacherId(0L);
            v.setAdmissionYear(c.getAdmissionYear());
            v.setStudentMaxCount(c.getStudentMaxCount());
            v.setClassState(c.getClassState());

            v.setTeacherName(clazzMapper.selectTeacherName(c.getTeacherId()));

            v.setStudentCount(clazzMapper.selectStuCount(c.getClassId().intValue()));

            return v;
        }).collect(Collectors.toList());

        int total = clazzMapper.countClazz(studentId,keyword);

        return PageRespDTO.<ClazzVO>builder()
                .list(voList)
                .total(total)
                .pages((total + size - 1) / size)
                .current(page)
                .size(size)
                .build();
    }
}