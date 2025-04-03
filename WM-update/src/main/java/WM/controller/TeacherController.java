package WM.controller;

import WM.core.BaseResponse;
import WM.dto.request.CreateTeacherInforRequestBody;
import WM.dto.request.PostCreateTeacherInfoByFileRequest;
import WM.dto.request.PostCreateTeacherInfoRequestBody;
import WM.dto.response.CreateTeacherInforRespone;
import WM.dto.response.PostCreateClassInfoByFileResponse;
import WM.dto.response.PostCreateTeacherInfoResponse;
import WM.exception.BadRequestException;
import WM.exception.VmListException;
import WM.service.impl.TeacherServiceImpl;
import WM.util.Constain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
//    @DeleteMapping("/")
//    public ResponseEntity<String>deleteTeacher(int teacherId){
//        teacherService.deleteTeacher(teacherId);
//        return ResponseEntity.ok("Delete Successful");
//    }
//    @PutMapping("/{teacherId}")
//    public ResponseEntity<TeacherDTO>updateTeacher(@PathVariable int teacherId,  @RequestBody TeacherDTO teacherDTO){
//        try{
//            TeacherDTO updatedTeacher = teacherService.updateTeacher(teacherDTO,teacherId);
//            return ResponseEntity.ok(updatedTeacher);
//        }catch (IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//    @GetMapping("/teacher")
//    public Page<TeacherDTO> getTeacher(
//            @RequestParam(required = false) String name,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        return teacherService.searchTeacher(name, page, size);
//    }
    @GetMapping("/list")
   public Page<PostCreateTeacherInfoResponse> getTeachers(Pageable pageable) {
       return teacherService.getAllTeachers(pageable);
    }
    @PostMapping("/import")
    public BaseResponse<?> importTeachersFromExcel(@Valid @ModelAttribute PostCreateTeacherInfoByFileRequest request, HttpServletRequest httpServletRequest, BindingResult result)throws Exception, BadRequestException {
        PostCreateClassInfoByFileResponse response=teacherService.importTeachersFromExcel(request,httpServletRequest);
       return new BaseResponse<>(response);
    }
     @PostMapping("/create")
    public BaseResponse<?> postCreatTeacherInfo(@Valid @RequestBody CreateTeacherInforRequestBody requestBody, HttpServletRequest httpServletRequest, BindingResult result)throws Exception, BadRequestException{
      CreateTeacherInforRespone response= teacherService.postCreateTeacherInfo(requestBody,httpServletRequest);
        return new BaseResponse<>(response);
    }
}
