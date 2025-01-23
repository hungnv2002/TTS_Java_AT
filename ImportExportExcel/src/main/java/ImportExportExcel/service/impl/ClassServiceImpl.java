package ImportExportExcel.service.impl;

import ImportExportExcel.dto.model.DTOMapper;
import ImportExportExcel.dto.request.ClassRequest;
import ImportExportExcel.dto.response.ClassResponse;
import ImportExportExcel.dto.response.StudentResponse;
import ImportExportExcel.dto.response.TeacherResponse;
import ImportExportExcel.entity.ClassEntity;
import ImportExportExcel.entity.Teacher;
import ImportExportExcel.exception.ClassImportException;
import ImportExportExcel.repository.ClassRepository;
import ImportExportExcel.repository.TeacherRepository;
import ImportExportExcel.service.ClassService;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    DTOMapper dtoMapper;


    @Override
    @Transactional
    public List<String> importClassesFromExcel(MultipartFile file) throws IOException {
        List<String> errors = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String classCode = row.getCell(1).getStringCellValue();
                    String name = row.getCell(2).getStringCellValue();
                    String teacherCode = row.getCell(3).getStringCellValue();

                    if (classRepository.existsByClassCode(classCode)) {
                        errors.add("Class code already exists: " + classCode);
                        continue;
                    }

                    Optional<Teacher> teacherOptional = teacherRepository.findByTeacherCode(teacherCode);
                    if (teacherOptional.isEmpty()) {
                        errors.add("Teacher not found for code: " + teacherCode);
                        continue;
                    }

                    ClassEntity classEntity = new ClassEntity();
                    classEntity.setClassCode(classCode);
                    classEntity.setName(name);
                    classEntity.setTeacher(teacherOptional.get());

                    classRepository.save(classEntity);

                } catch (Exception e) {
                    errors.add("Error processing row " + (i + 1) + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage(), e);
        }

        if (!errors.isEmpty()) {
            throw new ClassImportException(errors);
        }
        return errors;
    }

    @Override
    public byte[] exportClassesToExcel() throws IOException {

        ClassPathResource resource = new ClassPathResource("template-class.xlsx");
        Workbook workbook = new XSSFWorkbook(resource.getInputStream());

        Sheet sheet = workbook.getSheetAt(0);


        CellStyle redStyle = workbook.createCellStyle();
        redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        List<ClassResponse> classes = classRepository.findAll()
                .stream()
                .map(dtoMapper::toClassResponse)
                .toList();

        int startRow = 3;

        for (ClassResponse classResponse : classes) {
            Row row = sheet.createRow(startRow++);


            Cell classCodeCell = row.createCell(1);
            if (classResponse.getClassCode() == null || classResponse.getClassCode().trim().isEmpty()) {
                classCodeCell.setCellValue("N/A"); // Default value
                classCodeCell.setCellStyle(redStyle); // Highlight in red
            } else {
                classCodeCell.setCellValue(classResponse.getClassCode());
            }
            Cell nameCell = row.createCell(2);
            if (classResponse.getName() == null || classResponse.getName().trim().isEmpty()) {
                nameCell.setCellValue("N/A");
                nameCell.setCellStyle(redStyle);
            } else {
                nameCell.setCellValue(classResponse.getName());
            }
            Cell teacherCell = row.createCell(3);
            TeacherResponse teacher = classResponse.getTeacher();
            if (teacher == null || teacher.getName() == null || teacher.getName().trim().isEmpty()) {
                teacherCell.setCellValue("N/A");
                teacherCell.setCellStyle(redStyle);
            } else {
                teacherCell.setCellValue(teacher.getName());
            }


        }
        sheet.setColumnWidth(1, 6000); // Class Code
        sheet.setColumnWidth(2, 8000); // Class Name
        sheet.setColumnWidth(3, 8000); // Teacher Name
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
    @Override
    public byte[] exportClassesErrorToExcel() throws IOException {

        ClassPathResource resource = new ClassPathResource("template-class.xlsx");
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
        List<ClassResponse> classes = classRepository.findAll()
                .stream()
                .map(dtoMapper::toClassResponse)
                .toList();
        int startRow = 3;

        for (ClassResponse classResponse : classes) {
            StringBuilder errorMessage = new StringBuilder();
            if (classResponse.getClassCode() == null || classResponse.getClassCode().trim().isEmpty()) {
                errorMessage.append("Mã lớp trống; ");
            }
            if (classResponse.getName() == null || classResponse.getName().trim().isEmpty()) {
                errorMessage.append("Tên lớp trống; ");
            }
            TeacherResponse teacher = classResponse.getTeacher();
            if (teacher == null || teacher.getName() == null || teacher.getName().trim().isEmpty()) {
                errorMessage.append("Giáo viên không hợp lệ; ");
            }
            if (errorMessage.length() > 0) {
                Row row = sheet.createRow(startRow++);
                Cell classCodeCell = row.createCell(1);
                classCodeCell.setCellValue(classResponse.getClassCode() == null ? "N/A" : classResponse.getClassCode());
                if (classResponse.getClassCode() == null || classResponse.getClassCode().trim().isEmpty()) {
                    classCodeCell.setCellStyle(redStyle); // Highlight in red
                }
                Cell nameCell = row.createCell(2);
                nameCell.setCellValue(classResponse.getName() == null ? "N/A" : classResponse.getName());
                if (classResponse.getName() == null || classResponse.getName().trim().isEmpty()) {
                    nameCell.setCellStyle(redStyle);
                }
                Cell teacherCell = row.createCell(3);
                if (teacher == null || teacher.getName() == null || teacher.getName().trim().isEmpty()) {
                    teacherCell.setCellValue("N/A");
                    teacherCell.setCellStyle(redStyle);
                } else {
                    teacherCell.setCellValue(teacher.getName());
                }
                Cell errorCell = row.createCell(4);
                errorCell.setCellValue(errorMessage.toString().trim().replaceAll(";", "\n"));
                errorCell.setCellStyle(errorCellStyle);
            }
        }


        sheet.setColumnWidth(1, 6000); // Class Code
        sheet.setColumnWidth(2, 8000); // Class Name
        sheet.setColumnWidth(3, 8000); // Teacher Name
        sheet.setColumnWidth(4, 12000); // Error Message

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }


}


