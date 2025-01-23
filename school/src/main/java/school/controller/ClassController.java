package school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.dto.ClassDTO;
import school.entity.ClassEntity;
import school.service.impl.ClassServiceImpl;

import java.util.List;

@RestController
@RequestMapping("class")
public class ClassController {
    @Autowired
    ClassServiceImpl classService;
    @PutMapping("/{classId}")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable int classId, @RequestBody ClassDTO classDTO) {
        try {
            ClassDTO updatedClass = classService.updateClass(classId, classDTO);
            return ResponseEntity.ok(updatedClass);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/")
    public ResponseEntity<String> deleteClass(int classId){
        classService.deleteClass(classId);
        return ResponseEntity.ok("Delete succesful");
    }
    @GetMapping("/classes")
    public List<ClassEntity> getClasses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return classService.searchClasses(name, code, page, size);
    }
    @GetMapping
    public List<ClassEntity> getClasses(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return classService.getAllClasses(page, size);
    }



}
