package WM.controller;

import WM.core.BaseResponse;
import WM.dto.request.PostCreateTeacherInfoByFileRequest;
import WM.dto.request.PostCreateTeacherInfoRequestBody;
import WM.dto.response.PostCreateClassInfoByFileResponse;
import WM.dto.response.PostCreateTeacherInfoResponse;
import WM.exception.VmListException;
import WM.service.impl.TeacherServiceImpl;
import WM.util.Constain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
//    @GetMapping
//    public List<TeacherDTO> getTeachers(@RequestParam(defaultValue = "0") int page,
//                                        @RequestParam(defaultValue = "10") int size) {
//        return teacherService.getAllTeachers(page, size);
//    }
    @PostMapping("/import")
    public BaseResponse<?> importTeachersFromExcel(@Valid @ModelAttribute PostCreateTeacherInfoByFileRequest request, HttpServletRequest httpServletRequest, BindingResult result)throws Exception, VmListException {
        if (result.hasErrors()){
            throw new VmListException(Constain.ERRORCODE.BAD_REQUEST, Constain.TEACHER.LIST_ERRORS,result.getAllErrors());
        }
        PostCreateClassInfoByFileResponse response=teacherService.importTeachersFromExcel(request,httpServletRequest);
       return new BaseResponse<>(response);
    }
     @PostMapping("/create")
    public BaseResponse<?> postCreatTeacherInfo(@Valid @RequestBody PostCreateTeacherInfoRequestBody requestBody, HttpServletRequest httpServletRequest, BindingResult result)throws Exception, VmListException{
        if (result.hasErrors()){
            throw new VmListException(Constain.ERRORCODE.BAD_REQUEST, Constain.TEACHER.LIST_ERRORS, result.getAllErrors());
        }
        PostCreateTeacherInfoResponse response= teacherService.postCreateTeacherInfo(requestBody,httpServletRequest);
        return new BaseResponse<>(response);
    }
}
