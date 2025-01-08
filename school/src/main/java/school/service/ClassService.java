package school.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.dto.ClassDTO;
import school.dto.TeacherDTO;
import school.entity.ClassEntity;
import school.entity.Teacher;
import school.repository.ClassRepo;
import school.repository.StudentRepo;
import school.repository.TeacherRepo;

@Service
public class ClassService {
    @Autowired
    ClassRepo classRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    StudentRepo studentRepo;

    public ClassDTO addClass(ClassDTO classDTO) {
        Teacher teacher = teacherRepo.findById(classDTO.getTeacher().getId())
                .orElseThrow(() -> new RuntimeException("Giáo viên không tồn tại"));
        ClassEntity classEntity = new ClassEntity();
        classEntity.setCode(classDTO.getCode());
        classEntity.setName(classDTO.getName());
        classEntity.setTeacher(teacher);
        classRepo.save(classEntity);
        return classDTO;
    }
    @Transactional
    public ClassDTO  updateClass(int classId, ClassDTO classDTO){
        ClassEntity classEntity = classRepo.findById(classId).orElse(null);
        if (classEntity == null) {
            throw new IllegalArgumentException("Lớp học không tồn tại");
        }else
        {
            classEntity.setName(classDTO.getName());
            classEntity.setCode(classDTO.getCode());
            Teacher teacher = teacherRepo.findById(classDTO.getTeacher().getId())
                    .orElseThrow(() -> new RuntimeException("Giáo viên không tồn tại"));
            classEntity.setTeacher(teacher);
            classRepo.save(classEntity);
            return classDTO;
        }
    }
    @Transactional
    public void deleteClass(int classId){
        ClassEntity classEntity = classRepo.findById(classId).orElse(null);
        if (classEntity == null) {
            throw new IllegalArgumentException("Lớp học không tồn tại");
        }
        boolean hasStudents = studentRepo.existsByClassEntityId(classId);
        if (hasStudents) {
            throw new IllegalArgumentException("Lớp học có học sinh, không thể xóa");
        }
        classRepo.delete(classEntity);
    }
    @Autowired
    private ModelMapper modelMapper;

    public Page<ClassDTO> searchClasses(String name, String code, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClassEntity> classPage = classRepo.findByNameContainingOrCodeContaining(name, code, pageable);
        // Chuyển đổi từ Page<Class> sang Page<ClassDTO>
        Page<ClassDTO> classDTOPage = classPage.map(classEntity -> modelMapper.map(classEntity, ClassDTO.class));

        return classDTOPage;
    }
    public Page<ClassDTO> getAllClasses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClassEntity> classesPage = classRepo.findAll(pageable);
        return classesPage.map(classEntity -> modelMapper.map(classEntity, ClassDTO.class));
    }

}
