package school.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.dto.ClassDTO;
import school.dto.StudentDTO;
import school.entity.ClassEntity;
import school.entity.Student;
import school.repository.ClassRepo;
import school.repository.StudentRepo;

@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    ClassRepo classRepo;
    public StudentDTO addStudent(StudentDTO studentDTO){
        Student student= new Student();
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setAddress(studentDTO.getAddress());
        student.setEmail(studentDTO.getEmail());
        ClassEntity classEntity = classRepo.findById(studentDTO.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Class with code " + studentDTO.getClassId() + " not found"));
        student.setClassEntity(classEntity);
        studentRepo.save(student);
        return  studentDTO;
    }
    @Transactional
    public void deleleStudent(int studentId){
        Student student=studentRepo.findById(studentId).orElse(null);
        if(student!=null){
            studentRepo.deleteById(studentId);
        }else
            throw new IllegalArgumentException("Hoc sinh khong ton tai ");
    }
    @Transactional
    public  StudentDTO updateStudent(int studentId, StudentDTO studentDTO){
        Student student=studentRepo.findById(studentId).orElse(null);
        if(student==null){
            throw  new IllegalArgumentException(("Hoc sinh khong ton tai"));
        }else
            student.setEmail(studentDTO.getEmail());
        studentRepo.save(student);
        return studentDTO;
    }
    @Autowired
    ModelMapper modelMapper;
    public Page<StudentDTO> searchStudent(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentsPage = studentRepo.findByNameContaining(name, pageable);
        Page<StudentDTO> StudentDTOPage = studentsPage.map(student -> modelMapper.map(student, StudentDTO.class));

        return StudentDTOPage;
    }
    public Page<StudentDTO> getAllStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentsPage = studentRepo.findAll(pageable);
        return studentsPage.map(student -> modelMapper.map(student, StudentDTO.class));
    }



}

