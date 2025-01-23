package ImportExportExcel.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {
    void importStudentsFromExcel(MultipartFile file) throws IOException;

    // Phương thức để xuất dữ liệu sinh viên ra file Excel (nếu cần)
    byte[] exportStudentsToExcel() throws IOException;

    // Phương thức để xuất dữ liệu sinh viên lỗi ra file Excel (nếu cần)
    byte[] exportStudentsErrorToExcel() throws IOException;
}
