package ImportExportExcel.service;

import ImportExportExcel.entity.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<String> importTeachersFromExcel(MultipartFile file) throws IOException;
    byte[] exportTeachersToExcel() throws IOException;
    byte[] exportTeachersErrorToExcel() throws IOException;
}
