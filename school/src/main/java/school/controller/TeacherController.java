package school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.dto.StudentDTO;
import school.dto.TeacherDTO;
import school.entity.Teacher;
import school.repository.TeacherRepo;
import school.service.TeacherService;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    TeacherRepo teacherRepo;
    @PostMapping
    public ResponseEntity<TeacherDTO> addTeacher(@RequestBody TeacherDTO teacherDTO){
        TeacherDTO createdTeacher=teacherService.addTeacher(teacherDTO);
        return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
    }
    @DeleteMapping("/")
    public ResponseEntity<String>deleteTeacher(int teacherId){
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.ok("Delete Successful");
    }
    @PutMapping("/{teacherId}")
    public ResponseEntity<TeacherDTO>updateTeacher(@PathVariable int teacherId,  @RequestBody TeacherDTO teacherDTO){
        try{
            TeacherDTO updatedTeacher = teacherService.updateTeacher(teacherDTO,teacherId);
            return ResponseEntity.ok(updatedTeacher);
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/teacher")
    public Page<TeacherDTO> getTeacher(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return teacherService.searchTeacher(name, page, size);
    }
    @GetMapping
    public Page<TeacherDTO> getTeachers(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return teacherService.getAllTeachers(page, size);
    }

}
