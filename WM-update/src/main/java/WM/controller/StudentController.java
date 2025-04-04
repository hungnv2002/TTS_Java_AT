//package WM.controller;
//
//import WM.service.impl.StudentServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("student")
//public class StudentController {
//    @Autowired
//    StudentServiceImpl studentService;
//    @PostMapping("/")
//    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO){
//        StudentDTO addedStudent=studentService.addStudent(studentDTO);
//        return new  ResponseEntity<>(studentDTO, HttpStatus.CREATED);
//    }
//    @DeleteMapping("/")
////    public ResponseEntity<String>deleteStudent(int studentId){
//        studentService.deleteStudent(studentId);
//        return ResponseEntity.ok("delete succesful");
//    }
//    @PutMapping("/{studentId}")
//    public ResponseEntity<StudentDTO>updateStudent(@PathVariable int studentId, @RequestBody StudentDTO studentDTO){
//        try{
//            StudentDTO updatedStudent = studentService.updateStudent(studentId,studentDTO);
//            return ResponseEntity.ok(updatedStudent);
//        }catch (IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//    @GetMapping("/students")
//    public List<StudentDTO> getStudent(
//            @RequestParam(required = false) String name,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        return studentService.searchStudent(name, page, size);
//    }
//    @GetMapping
//    public List<StudentDTO> getStudents(@RequestParam(defaultValue = "0") int page,
//                                        @RequestParam(defaultValue = "10") int size) {
//        return studentService.getAllStudents(page, size);
//    }
//}
//
