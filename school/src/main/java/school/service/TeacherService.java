package school.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import school.dto.TeacherDTO;

import java.io.IOException;
import java.util.List;

public interface TeacherService {
    TeacherDTO updateTeacher(TeacherDTO teacherDTO, int teacherId);
    void deleteTeacher(int teacherId);
    Page<TeacherDTO> searchTeacher(String name, int page, int size);
    List<TeacherDTO> getAllTeachers(int page, int size);
    List<String> importTeachersFromExcel(MultipartFile file) throws IOException;
}
