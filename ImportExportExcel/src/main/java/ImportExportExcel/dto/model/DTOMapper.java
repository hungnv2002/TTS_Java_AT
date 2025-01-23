package ImportExportExcel.dto.model;

import ImportExportExcel.dto.request.ClassRequest;
import ImportExportExcel.dto.request.StudentRequest;
import ImportExportExcel.dto.request.TeacherRequest;
import ImportExportExcel.dto.response.ClassResponse;
import ImportExportExcel.dto.response.StudentResponse;
import ImportExportExcel.dto.response.TeacherResponse;
import ImportExportExcel.entity.ClassEntity;
import ImportExportExcel.entity.Student;
import ImportExportExcel.entity.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public ClassResponse toClassResponse(ClassEntity entity) {
        return modelMapper.map(entity, ClassResponse.class);
    }

    public ClassEntity toClassEntity(ClassRequest request) {
        return modelMapper.map(request, ClassEntity.class);
    }

    public StudentResponse toStudentResponse(Student entity) {
        return modelMapper.map(entity, StudentResponse.class);
    }

    public Student toStudentEntity(StudentRequest request) {
        return modelMapper.map(request, Student.class);
    }

    public TeacherResponse toTeacherResponse(Teacher entity) {
        return modelMapper.map(entity, TeacherResponse.class);
    }

    public Teacher toTeacherEntity(TeacherRequest request) {
        return modelMapper.map(request, Teacher.class);
    }
}

