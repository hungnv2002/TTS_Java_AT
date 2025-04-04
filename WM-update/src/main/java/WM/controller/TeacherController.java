package WM.controller;

import WM.core.BaseResponse;

import WM.dto.request.*;
import WM.dto.response.CreateTeacherInforRespone;
import WM.dto.response.PostCreateClassInfoByFileResponse;
import WM.dto.response.PostCreateTeacherInfoResponse;
import WM.dto.response.UpdateTeacherInforResponseBody;
import WM.exception.BadRequestException;
import WM.exception.VmListException;
import WM.service.impl.TeacherServiceImpl;
import WM.util.Constain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    TeacherServiceImpl teacherService;
    @DeleteMapping("/")
    public ResponseEntity<String>deleteTeacher(@RequestBody DeleteTeacherInfoRequestBody requestBody, BindingResult result){
        teacherService.deleteTeacher(requestBody);
        return ResponseEntity.ok("Delete Successful");
    }
    @PutMapping("/")
    public BaseResponse<?>updateTeacher(@RequestBody UpdateTeacherInforRequestBody requestBody, BindingResult result){
        if(result.hasErrors()){
            throw  new BadRequestException(HttpStatus.BAD_REQUEST.value(), Constain.TEACHER.LIST_ERRORS);
        }
       UpdateTeacherInforResponseBody responseBody= teacherService.updateTeacherInfor(requestBody);
        return new BaseResponse<>(responseBody);

    }
    @GetMapping("/list")
   public Page<PostCreateTeacherInfoResponse> getTeachers(Pageable pageable) {
       return teacherService.getAllTeachers(pageable);
    }
    @PostMapping("/import")
    public BaseResponse<?> importTeachersFromExcel(@Valid @ModelAttribute PostCreateTeacherInfoByFileRequest request, HttpServletRequest httpServletRequest, BindingResult result)throws Exception, BadRequestException {
        if(result.hasErrors()){
            throw  new BadRequestException(HttpStatus.BAD_REQUEST.value(), Constain.TEACHER.LIST_ERRORS);
        }
        PostCreateClassInfoByFileResponse response=teacherService.importTeachersFromExcel(request,httpServletRequest);
       return new BaseResponse<>(response);
    }
     @PostMapping("/create")
    public BaseResponse<?> postCreatTeacherInfo(@Valid @RequestBody CreateTeacherInforRequestBody requestBody, HttpServletRequest httpServletRequest, BindingResult result)throws Exception, BadRequestException{
         if(result.hasErrors()){
             throw  new BadRequestException(HttpStatus.BAD_REQUEST.value(), Constain.TEACHER.LIST_ERRORS);
         }
      CreateTeacherInforRespone response= teacherService.postCreateTeacherInfo(requestBody,httpServletRequest);
        return new BaseResponse<>(response);
    }
}
