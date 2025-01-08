package school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.dto.ClassDTO;
import school.entity.ClassEntity;
import school.service.ClassService;

@RestController
@RequestMapping("class")
public class ClassController {
    @Autowired
    ClassService classService;
    @PostMapping
    public ResponseEntity<ClassDTO>addClass(@RequestBody ClassDTO classDTO){
        ClassDTO addedClass= classService.addClass(classDTO);
        return new ResponseEntity<>(addedClass, HttpStatus.CREATED);
    }
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
    public Page<ClassDTO> getClasses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return classService.searchClasses(name, code, page, size);
    }
    @GetMapping
    public Page<ClassDTO> getClasses(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return classService.getAllClasses(page, size);
    }

}
