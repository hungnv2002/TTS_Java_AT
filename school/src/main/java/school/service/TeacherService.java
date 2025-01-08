package school.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.dto.StudentDTO;
import school.dto.TeacherDTO;
import school.entity.Student;
import school.entity.Teacher;
import school.repository.TeacherRepo;
@Service
public class TeacherService {
    @Autowired
    TeacherRepo teacherRepo;
    public TeacherDTO addTeacher(TeacherDTO teacherDTO){
        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setAddress(teacherDTO.getAddress());
        teacher.setAge(teacherDTO.getAge());
        teacherRepo.save(teacher);
        return teacherDTO;
    }
    @Transactional
    public void deleteTeacher(int teacherId){
        Teacher teacher=teacherRepo.findById(teacherId).orElse(null);
        if(teacher!=null){
            teacherRepo.deleteById(teacherId);
        }else
            throw new IllegalArgumentException("Teacher not exsit");
    }
    @Transactional
    public TeacherDTO updateTeacher(TeacherDTO teacherDTO, int teacherId){
        Teacher teacher=teacherRepo.findById(teacherId).orElse(null);
        if(teacher ==null){
            throw new IllegalArgumentException("Teacher not exsit");
        }
            teacher.setName(teacherDTO.getName());
            teacher.setEmail(teacherDTO.getEmail());
            teacher.setAddress(teacherDTO.getAddress());
            teacher.setAge(teacherDTO.getAge());
            teacherRepo.save(teacher);
            return teacherDTO;
    }
    @Autowired
    ModelMapper modelMapper;
    public Page<TeacherDTO> searchTeacher(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Teacher> studentsPage = teacherRepo.findByNameContaining(name, pageable);
        Page<TeacherDTO> teacherDTOSPage = studentsPage.map(teacher -> modelMapper.map(teacher, TeacherDTO.class));

        return teacherDTOSPage;
    }
    public Page<TeacherDTO> getAllTeachers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Teacher> teachersPage = teacherRepo.findAll(pageable);
        return teachersPage.map(teacher -> modelMapper.map(teacher, TeacherDTO.class));
    }
}
