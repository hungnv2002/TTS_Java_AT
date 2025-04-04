package WM.service.impl;

import WM.dto.StudentInforCommon;
import WM.dto.TeacherCommon;
import WM.dto.request.PostCreateStudentInfoByFileRequestBody;
import WM.dto.response.PostCreateStudentInforResponseBody;
import WM.exception.BadRequestException;
import WM.service.StudentService;
import WM.util.Constain;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class StudentServiceImpl implements StudentService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(ModelMapper modelMapper, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.modelMapper = modelMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public PostCreateStudentInforResponseBody postCreateStudentInfoByFile(PostCreateStudentInfoByFileRequestBody requestBody, HttpServletRequest httpServletRequest) throws BadRequestException, IOException {
        List<String> errorMessages = new ArrayList<>();
        List<StudentInforCommon> validStudent = new ArrayList<>();
        Set<String> studentCodeFormFile = new HashSet<>();
        Set<Integer> classIdsFromFile = new HashSet<>();
        Workbook workbook = new XSSFWorkbook(requestBody.getMultipartFile().getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        int currentRowIndex = 0;
        while (rowIterator.hasNext()){
            Row row= rowIterator.next();
            currentRowIndex ++;
            if (currentRowIndex<3) continue;
            if (isRowEmpty(row)) break;
            String studentCode= getCellValueAsString(row.getCell(1));
            String name=getCellValueAsString(row.getCell(2));
            Integer classId= getCellValueAsInteger(row.getCell(3));
            if(StringUtils.isBlank(studentCode)){
                errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": " +  Constain.TEACHER.TEACHER_CODE_BLANK);
                continue;
            }
            if (StringUtils.isBlank(name)) {
                errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": " + Constain.TEACHER.TEACHER_NAME_BLANK);
                continue;
            }
            if(classId==null) {
                errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": " + Constain.TEACHER.TEACHER_CODE_BLANK);
                continue;
            }
            if (studentCodeFormFile.contains(studentCode)) {
                errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": " + studentCode + " " + Constain.TEACHER.TEACHER_CODE_EXSIST);
                continue;
            }
            studentCodeFormFile.add(studentCode);
            validStudent.add(new StudentInforCommon(studentCode, name, classId));
        }
        workbook.close();
        if (!classIdsFromFile.isEmpty()) {
            String sql = "SELECT id FROM class WHERE id IN (:classIds)";
            Map<String, Object> params = Map.of("classIds", classIdsFromFile);
            List<Integer> existingClassIds = namedParameterJdbcTemplate.queryForList(sql, params, Integer.class);

            validStudent.removeIf(student -> {
                if (!existingClassIds.contains(student.getClassId())) {
                    errorMessages.add("Dòng chứa mã sinh viên '" + student.getStudentCode() + "': Lớp có ID " + student.getClassId() + " không tồn tại trong hệ thống.");
                    return true;
                }
                return false;
            });
        }
        PostCreateStudentInforResponseBody response = new PostCreateStudentInforResponseBody();
        response.setErrorMessages(errorMessages);
        response.setSuccessCount(validStudent.size());

        return response;
    }



    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(getCellValueAsString(cell))) {
                return false;
            }
        }
        return true;
    }
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:

                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                }
                double numericValue = cell.getNumericCellValue();
                if (numericValue == (int) numericValue) {
                    return String.valueOf((int) numericValue);
                }
                return String.valueOf(numericValue);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    private Integer getCellValueAsInteger(Cell cell) {
        if (cell == null) {
            return null; // Trả về null nếu ô trống
        }
        try {
            return (int) cell.getNumericCellValue(); // Chuyển đổi số thực về số nguyên
        } catch (Exception e) {
            return null; // Trả về null nếu lỗi (ví dụ: ô chứa chữ)
        }
    }

}

