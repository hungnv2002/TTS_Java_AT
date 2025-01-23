package ImportExportExcel.service.impl;

import ImportExportExcel.entity.ClassEntity;
import ImportExportExcel.entity.Student;
import ImportExportExcel.repository.ClassRepository;
import ImportExportExcel.repository.StudentRepository;
import ImportExportExcel.service.StudentService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ClassRepository classRepository;

    @Override
    public void importStudentsFromExcel(MultipartFile file) throws IOException {
        // Đọc dữ liệu từ file Excel
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        // Lấy danh sách các sinh viên từ file Excel, bắt đầu từ hàng thứ 4 (index 3)
        int startRow = 3;

        for (int i = startRow; i <= sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            // Lấy dữ liệu từ các cột trong Excel
            String studentCode = row.getCell(1) != null ? row.getCell(1).getStringCellValue().trim() : "";
            String studentName = row.getCell(2) != null ? row.getCell(2).getStringCellValue().trim() : "";
            String classCode = row.getCell(3) != null ? row.getCell(3).getStringCellValue().trim() : "";


            if (studentCode.isEmpty() || studentName.isEmpty() || classCode.isEmpty()) {
                continue; // Bỏ qua sinh viên nếu có lỗi (có thể thêm logic để lưu lỗi)
            }

            // Tìm kiếm ClassEntity từ classCode
            ClassEntity classEntity = (ClassEntity) classRepository.findByClassCode(classCode);
            if (classEntity == null) {
                continue; // Nếu không tìm thấy lớp tương ứng, bỏ qua sinh viên này
            }

            // Tạo và lưu sinh viên
            Student student = new Student();
            student.setStudentCode(studentCode);
            student.setName(studentName);
            student.setClassEntity(classEntity);
            studentRepository.save(student);
        }

        workbook.close();
    }

    @Override
    public byte[] exportStudentsToExcel() throws IOException {
        return new byte[0];
    }

    @Override
    public byte[] exportStudentsErrorToExcel() throws IOException {
        // Load the template Excel file
        ClassPathResource resource = new ClassPathResource("template-student.xlsx");
        Workbook workbook = new XSSFWorkbook(resource.getInputStream());

        Sheet sheet = workbook.getSheetAt(0);

        CellStyle redStyle = workbook.createCellStyle();
        redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        CellStyle errorCellStyle = workbook.createCellStyle();
        errorCellStyle.setBorderBottom(BorderStyle.THIN);
        errorCellStyle.setBorderTop(BorderStyle.THIN);
        errorCellStyle.setBorderLeft(BorderStyle.THIN);
        errorCellStyle.setBorderRight(BorderStyle.THIN);
        errorCellStyle.setWrapText(true);


        List<Student> students = studentRepository.findAll();

        int startRow = 3;

        for (Student student : students) {
            StringBuilder errorMessage = new StringBuilder();

            if (student.getStudentCode() == null || student.getStudentCode().trim().isEmpty()) {
                errorMessage.append("Mã sinh viên trống; ");
            }
            if (student.getName() == null || student.getName().trim().isEmpty()) {
                errorMessage.append("Tên sinh viên trống; ");
            }

            // Check Class Code
            if (student.getClassEntity() == null || student.getClassEntity().getClassCode() == null || student.getClassEntity().getClassCode().trim().isEmpty()) {
                errorMessage.append("Mã lớp không hợp lệ; ");
            }
            if (errorMessage.length() > 0) {
                Row row = sheet.createRow(startRow++);

                Cell studentCodeCell = row.createCell(1);
                studentCodeCell.setCellValue(student.getStudentCode() == null ? "N/A" : student.getStudentCode());
                if (student.getStudentCode() == null || student.getStudentCode().trim().isEmpty()) {
                    studentCodeCell.setCellStyle(redStyle); // Highlight in red
                }

                Cell nameCell = row.createCell(2);
                nameCell.setCellValue(student.getName() == null ? "N/A" : student.getName());
                if (student.getName() == null || student.getName().trim().isEmpty()) {
                    nameCell.setCellStyle(redStyle);
                }
                Cell classCodeCell = row.createCell(3);
                classCodeCell.setCellValue(student.getClassEntity() == null || student.getClassEntity().getClassCode() == null ? "N/A" : student.getClassEntity().getClassCode());
                if (student.getClassEntity() == null || student.getClassEntity().getClassCode().trim().isEmpty()) {
                    classCodeCell.setCellStyle(redStyle);
                }
                Cell errorCell = row.createCell(4);
                errorCell.setCellValue(errorMessage.toString().trim().replaceAll(";", "\n"));
                errorCell.setCellStyle(errorCellStyle);
            }
        }

        sheet.setColumnWidth(1, 6000); // Student Code
        sheet.setColumnWidth(2, 8000); // Student Name
        sheet.setColumnWidth(3, 8000); // Class Code
        sheet.setColumnWidth(4, 12000); // Error Message

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Return the Excel file as byte[]
        return outputStream.toByteArray();
    }

}
