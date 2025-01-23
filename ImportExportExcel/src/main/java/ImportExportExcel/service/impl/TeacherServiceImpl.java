package ImportExportExcel.service.impl;

import ImportExportExcel.entity.Teacher;
import ImportExportExcel.exception.InvalidInputException;
import ImportExportExcel.exception.TeacherImportException;
import ImportExportExcel.repository.TeacherRepository;
import ImportExportExcel.service.TeacherService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public List<String> importTeachersFromExcel(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new InvalidInputException("File must not be empty.");
        }
        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            throw new InvalidInputException("Invalid file type. Only .xlsx files are supported.");
        }

        List<String> errorMessages = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int currentRowIndex = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                currentRowIndex++;
                if (currentRowIndex < 4) {
                    continue;
                }
                if (isRowEmpty(row)) {
                    break;
                }

                Cell codeCell = row.getCell(1);
                if (codeCell == null || codeCell.getStringCellValue().trim().isEmpty()) {
                    errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": Mã giáo viên không được để trống.");
                    continue;
                }
                String teacherCode = codeCell.getStringCellValue().trim();
                Optional<Teacher> existingTeacher = teacherRepository.findByTeacherCode(teacherCode);
                if (existingTeacher != null) {
                    errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": Mã giáo viên '" + teacherCode + "' đã tồn tại.");
                    continue;
                }

                Cell nameCell = row.getCell(2);
                if (nameCell == null || nameCell.getStringCellValue().trim().isEmpty()) {
                    errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": Tên giáo viên không được để trống.");
                    continue;
                }

                String name = nameCell.getStringCellValue().trim();
                Teacher newTeacher = new Teacher();
                newTeacher.setTeacherCode(teacherCode);
                newTeacher.setName(name);
                teacherRepository.save(newTeacher);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file.", e);
        }

        if (!errorMessages.isEmpty()) {
            throw new TeacherImportException(errorMessages);
        }

        return errorMessages;
    }

    private boolean isRowEmpty(Row row) {
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.toString().trim().length() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public byte[] exportTeachersToExcel() throws IOException {
        // Đọc file mẫu từ thư mục resources
        ClassPathResource resource = new ClassPathResource("template-teacher.xlsx");
        Workbook workbook = new XSSFWorkbook(resource.getInputStream());

        // Lấy sheet đầu tiên trong file mẫu
        Sheet sheet = workbook.getSheetAt(0);

        // Tạo CellStyle để tô đỏ
        CellStyle redStyle = workbook.createCellStyle();
        redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Ghi dữ liệu vào file
        List<Teacher> teachers = teacherRepository.findAll();
        int startRow = 3; // Bắt đầu ghi từ hàng thứ 4 (giả sử hàng đầu là header)

        for (Teacher teacher : teachers) {
            Row row = sheet.createRow(startRow++);

            // Ghi mã giáo viên vào cột 1
            Cell teacherCodeCell = row.createCell(1);
            if (teacher.getTeacherCode() == null || teacher.getTeacherCode().trim().isEmpty()) {
                teacherCodeCell.setCellValue("N/A"); // Đặt giá trị mặc định
                teacherCodeCell.setCellStyle(redStyle); // Áp dụng style tô đỏ
            } else {
                teacherCodeCell.setCellValue(teacher.getTeacherCode());
            }

            // Ghi tên giáo viên vào cột 2
            Cell nameCell = row.createCell(2);
            if (teacher.getName() == null || teacher.getName().trim().isEmpty()) {
                nameCell.setCellValue("N/A"); // Đặt giá trị mặc định
                nameCell.setCellStyle(redStyle); // Áp dụng style tô đỏ
            } else {
                nameCell.setCellValue(teacher.getName());
            }
        }

        // Lưu workbook vào ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Trả về dữ liệu file dưới dạng byte[]
        return outputStream.toByteArray();
    }

    @Override
    public byte[] exportTeachersErrorToExcel() throws IOException {
        ClassPathResource resource = new ClassPathResource("template-teacher.xlsx");
        Workbook workbook = new XSSFWorkbook(resource.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(2);
        if (headerRow == null) {
            headerRow = sheet.createRow(2);
        }
        headerRow.createCell(3).setCellValue("Error");
        CellStyle redTextStyle = workbook.createCellStyle();
        Font redFont = workbook.createFont();
        redFont.setColor(IndexedColors.RED.getIndex());
        redTextStyle.setFont(redFont);

        CellStyle errorCellStyle = workbook.createCellStyle();
        errorCellStyle.setBorderBottom(BorderStyle.THIN);
        errorCellStyle.setBorderTop(BorderStyle.THIN);
        errorCellStyle.setBorderLeft(BorderStyle.THIN);
        errorCellStyle.setBorderRight(BorderStyle.THIN);
        errorCellStyle.setWrapText(true);
        List<Teacher> teachers = teacherRepository.findAll();
        int startRow = 3;
        for (Teacher teacher : teachers) {
            String errorMessage = "";
            if (teacher.getTeacherCode() == null || teacher.getTeacherCode().trim().isEmpty()) {
                errorMessage += "Mã giáo viên  trống; ";
            }
            if (teacher.getName() == null || teacher.getName().trim().isEmpty()) {
                errorMessage += "Tên giáo viên bị trống; ";
            }
            if (teacher.getName() != null && teacher.getName().length() > 50) {
                errorMessage += "Tên giáo viên vượt quá độ dài tối đa 50 ký tự; ";
            }
            if (!errorMessage.isEmpty()) {
                Row row = sheet.createRow(startRow++);
                row.createCell(1).setCellValue(teacher.getTeacherCode()); // Mã giáo viên
                row.createCell(2).setCellValue(teacher.getName() == null ? "" : teacher.getName()); // Tên giáo viên

                Cell errorCell = row.createCell(3);
                errorCell.setCellValue(errorMessage.trim().replaceAll(";", "\n")); // Xuống dòng giữa các lỗi
                errorCell.setCellStyle(redTextStyle);
                errorCell.setCellStyle(errorCellStyle);
            }
        }
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 8000);
        sheet.setColumnWidth(3, 10000);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }




}


