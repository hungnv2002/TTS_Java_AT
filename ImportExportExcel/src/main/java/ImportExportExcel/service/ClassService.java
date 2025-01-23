package ImportExportExcel.service;

import ImportExportExcel.entity.ClassEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClassService {
    // Xuất danh sách lớp học dưới dạng Excel
    byte[] exportClassesToExcel() throws IOException;

    // Xuất danh sách lớp học có lỗi dưới dạng Excel
    byte[] exportClassesErrorToExcel() throws IOException;

    List<String> importClassesFromExcel(MultipartFile file) throws IOException;
}
