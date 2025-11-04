package com.czy.template.service;

import com.czy.template.pojo.ClazzStudent;
import com.czy.template.util.JwtUtil;
import com.czy.template.util.Result;
import com.czy.template.view.vo.PageRespVO;
import com.czy.template.mapper.ClazzMapper;
import com.czy.template.pojo.Clazz;
import com.czy.template.view.vo.ClazzVO;
import com.czy.template.view.vo.UserVO;
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

    public PageRespVO<ClazzVO> getClazzVOPage(Integer page, Integer size, String keyword) {
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

        return PageRespVO.<ClazzVO>builder()
                .list(voList)
                .total(total)
                .pages((total + size - 1) / size)
                .current(page)
                .size(size)
                .build();
    }

    //教师端查看班级
    public PageRespVO<ClazzVO> viewClass(Integer page, Integer size, String keyword, HttpServletRequest req) {
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

        return PageRespVO.<ClazzVO>builder()
                .list(voList)
                .total(total)
                .pages((total + size - 1) / size)
                .current(page)
                .size(size)
                .build();
    }

    //学生端查看班级
    public PageRespVO<ClazzVO> studentViewClass(Integer page, Integer size, String keyword, HttpServletRequest req) {
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

        return PageRespVO.<ClazzVO>builder()
                .list(voList)
                .total(total)
                .pages((total + size - 1) / size)
                .current(page)
                .size(size)
                .build();
    }

    //学生端加入班级
    public Result<Void> joinClass(String classCode, HttpServletRequest req) {
        Clazz clazz = clazzMapper.selectClazzByClassCode(classCode);
        if(clazz == null){
           return Result.error("班级不存在");
        }

        Long studentId = jwtUtil.getUserFromRequest(req).getId();
        int clazzStudent1 = clazzMapper.selectClassStudent(clazz.getClassId(), studentId);
        if(clazzStudent1 != 0){
            return Result.error("已经申请进入班级！");
        }

        ClazzStudent clazzStudent = new ClazzStudent();
        clazzStudent.setClazzId(clazz.getClassId());
        clazzStudent.setStudentId(studentId);
        clazzStudent.setStudentCode(classCode);
        clazzStudent.setState(0);

        if(clazzMapper.insertStudent(clazzStudent) > 0){
            return Result.ok("加入班级成功",null);
        }
        return Result.error("加入班级失败，请稍后再试！");
    }

    public Result<List<UserVO>> manageClass(Long classId) {
        ArrayList<UserVO> userVOS = new ArrayList<>();
        List<Long> studentIds = clazzMapper.selectClazzStudentId(classId);

        if(studentIds == null || studentIds.size() == 0){
            return Result.error("");
        }

        for (Long studentId : studentIds) {
            UserVO userVO = clazzMapper.selectClazzStudent(studentId);
            if (userVO != null) {
                userVOS.add(userVO);
            }
        }

        if(userVOS == null || userVOS.size() == 0){
            return Result.error("");
        }

        return Result.ok(userVOS);
    }
}