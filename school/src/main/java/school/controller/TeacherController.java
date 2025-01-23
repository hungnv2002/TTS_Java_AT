package school.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.dto.TeacherDTO;
import school.repository.TeacherRepo;
import school.service.impl.TeacherServiceImpl;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherServiceImpl teacherService;
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
    public List<TeacherDTO> getTeachers(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return teacherService.getAllTeachers(page, size);
    }
    @PostMapping("/import")
    public ResponseEntity<String> importTeachersFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            teacherService.importTeachersFromExcel(file);
            return ResponseEntity.ok("Dữ liệu đã được nhập thành công!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Lỗi khi đọc file: " + e.getMessage());
        }
    }

}
