package WM.service.impl;

import WM.dto.TeacherCommon;
import WM.dto.request.PostCreateTeacherInfoByFileRequest;
import WM.dto.request.PostCreateTeacherInfoRequestBody;
import WM.dto.response.PostCreateClassInfoByFileResponse;
import WM.dto.response.PostCreateTeacherInfoResponse;
import WM.exception.VmException;
import WM.exception.VmListException;
import WM.service.TeacherService;
import WM.util.Constain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;
     private final ModelMapper modelMapper;

    public TeacherServiceImpl(ModelMapper modelMapper,NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.modelMapper = modelMapper;
        this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public PostCreateTeacherInfoResponse postCreateTeacherInfo(PostCreateTeacherInfoRequestBody requestBody, HttpServletRequest httpServletRequest) throws VmException, IOException {
        if (ObjectUtils.isEmpty(requestBody)){
            throw new VmException(HttpStatus.BAD_REQUEST.value(),Constain.ERRORCODE.BAD_REQUEST, Constain.TEACHER.LIST_ERRORS);
        }
        validateTeacherInfoFromRequestBody(requestBody);

        Integer countCode = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM teacher WHERE teacher_code = ?", Integer.class, requestBody.getTeacherCode());
        if (countCode != null && countCode > 0) {
            throw new VmException(HttpStatus.BAD_REQUEST.value(), Constain.ERRORCODE.BAD_REQUEST,Constain.TEACHER.TEACHER_CODE_EXSIST);
        }
        String sql = "INSERT INTO teacher (teacher_Code, name) VALUES (?,?)";
        jdbcTemplate.update(sql, requestBody.getTeacherCode(), requestBody.getName());
        return modelMapper.map(requestBody, PostCreateTeacherInfoResponse.class);
    }

    @Override
    public PostCreateClassInfoByFileResponse importTeachersFromExcel(PostCreateTeacherInfoByFileRequest request, HttpServletRequest httpServletRequest) throws IOException, VmException {
        List<String> errorMessages = new ArrayList<>();
        List<TeacherCommon> validTeachers = new ArrayList<>();
        Set<String> teacherCodesFromFile = new HashSet<>();

        Workbook workbook = new XSSFWorkbook(request.getMultipartFile().getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        int currentRowIndex = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            currentRowIndex++;
            if (currentRowIndex < 3) continue;

            if (isRowEmpty(row)) break;

            String teacherCode = getCellValueAsString(row.getCell(1));
            String name = getCellValueAsString(row.getCell(2));
            if (StringUtils.isBlank(teacherCode)) {
                errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": " + Constain.TEACHER.TEACHER_CODE_BLANK);
                continue;
            }
            if (StringUtils.isBlank(name)) {
                errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": " + Constain.TEACHER.TEACHER_NAME_BLANK);
                continue;
            }
            if (teacherCodesFromFile.contains(teacherCode)) {
                errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": " + teacherCode + " " + Constain.TEACHER.TEACHER_CODE_EXSIST);
                continue;
            }
            teacherCodesFromFile.add(teacherCode);
            validTeachers.add(new TeacherCommon(teacherCode, name));
        }
        workbook.close();


        if (!validTeachers.isEmpty()) {
            String sql = "SELECT teacher_code FROM teacher WHERE teacher_code IN (:codes)";
            Map<String, Object> params = Map.of("codes", teacherCodesFromFile);
            List<String> existingTeacherCodes = namedParameterJdbcTemplate.queryForList(sql, params, String.class);

            validTeachers.removeIf(teacher -> {
                if (existingTeacherCodes.contains(teacher.getTeacherCode())) {
                    errorMessages.add("Mã giáo viên '" + teacher.getTeacherCode() + "' đã tồn tại trong hệ thống.");
                    return true;
                }
                return false;
            });

            if (errorMessages.isEmpty() && !validTeachers.isEmpty()) {
                jdbcTemplate.batchUpdate(
                        "INSERT INTO teacher (teacher_code, name) VALUES (?, ?)",
                        validTeachers,
                        validTeachers.size(),
                        (ps, teacher) -> {
                            ps.setString(1, teacher.getTeacherCode());
                            ps.setString(2, teacher.getName());
                        }
                );
            }
        }


        PostCreateClassInfoByFileResponse response = new PostCreateClassInfoByFileResponse();
        response.setErrorMessages(errorMessages);
        response.setSuccessCount(validTeachers.size());
        return response;
    }


    private <T extends TeacherCommon>void validateTeacherInfoFromRequestBody(T request) throws VmException{
        if (StringUtils.isBlank(request.getTeacherCode())){
            throw new VmException(HttpStatus.BAD_REQUEST.value(), Constain.ERRORCODE.BAD_REQUEST, Constain.TEACHER.TEACHER_CODE_BLANK);
        }
        if (StringUtils.isBlank(request.getName())){
            throw new VmException(HttpStatus.BAD_REQUEST.value(), Constain.ERRORCODE.BAD_REQUEST, Constain.TEACHER.TEACHER_NAME_BLANK);
        }
    }


    @Transactional
    public void deleteTeacher(int teacherId) {
        String sql = "DELETE FROM teacher WHERE id = ?";
        jdbcTemplate.update(sql, teacherId);
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



//    }
//    private boolean isRowEmpty(Row row) {
//        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
//            Cell cell = row.getCell(i);
//            if (cell != null && cell.toString().trim().length() > 0) {
//                return false;
//            }
//        }
//        return true;
//    }
//    @Override
//    @Transactional
//    public TeacherDTO updateTeacher(TeacherDTO teacherDTO, int teacherId) {
//        String sql = "UPDATE teacher SET name = ? and SET teacher_code=?WHERE id = ?";
//        jdbcTemplate.update(sql,teacherDTO.getTeacherCode(), teacherDTO.getName(), teacherId);
//        return teacherDTO;
//    }
//private static class TeacherRowMapper implements RowMapper<TeacherDTO> {
//        @Override
//        public TeacherDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//            TeacherDTO teacherDTO = new TeacherDTO();
//            teacherDTO.setId(rs.getInt("id"));
//            teacherDTO.setName(rs.getString("name"));
//            return teacherDTO;
//        }
//    }

}
