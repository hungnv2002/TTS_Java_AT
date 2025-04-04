package WM.service.impl;

import WM.dto.ClassInfoCommon;
import WM.dto.request.PostCreateClassInforRequestBody;
import WM.dto.response.PostCreateClassInforResponseBody;
import WM.entity.ClassEntity;
import WM.entity.Teacher;
import WM.exception.VmException;
import WM.service.ClassService;
import WM.util.Constain;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.html.HTMLAppletElement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PostCreateClassInforResponseBody postCreateClassInfo(PostCreateClassInforRequestBody requestBody, HttpServletRequest request) throws VmException, IOException {
        if (ObjectUtils.isEmpty(requestBody))
        {
            throw new VmException(HttpStatus.BAD_REQUEST.value(), Constain.ERRORCODE.BAD_REQUEST, Constain.ClASS.LIST_ERRORS);

        }
        validateClassInfoFromRequestBody(requestBody);

        return null;
    }
    private <T extends ClassInfoCommon> void validateClassInfoFromRequestBody(T request) throws VmException {
        if(StringUtils.isBlank(request.getClassCode())){
            throw new VmException(HttpStatus.BAD_REQUEST.value(), Constain.ERRORCODE.BAD_REQUEST, Constain.ClASS.CLASS_CLASSCODE_BLANK);

        }
        if (StringUtils.isBlank(request.getName())){
            throw new VmException(HttpStatus.BAD_REQUEST.value(), Constain.ERRORCODE.BAD_REQUEST,Constain.ClASS.CLASS_NAME_BLANK);
        }
        if (StringUtils.isBlank(request.getTeacherCode())){
            throw new VmException(HttpStatus.BAD_REQUEST.value(),Constain.ERRORCODE.BAD_REQUEST,Constain.TEACHER.TEACHER_NOTFOUND);
        }
    }

//    @Override
//    @Transactional
//    public ClassDTO updateClass(int classId, ClassDTO classDTO) {
//        String classSql = "SELECT * FROM class WHERE id = ?";
//        List<ClassEntity> classes = jdbcTemplate.query(classSql, new Object[]{classId}, new ClassRowMapper());
//        if (classes.isEmpty()) {
//            throw new IllegalArgumentException("Lớp học không tồn tại");
//        }
//        String teacherSql = "SELECT * FROM teacher WHERE id = ?";
//        List<Teacher> teachers = jdbcTemplate.query(teacherSql, new Object[]{classDTO.getTeacher().getId()}, new TeacherRowMapper());
//        if (teachers.isEmpty()) {
//            throw new RuntimeException("Giáo viên không tồn tại");
//        }
//        String updateSql = "UPDATE class SET code = ?, name = ?, teacher_id = ? WHERE id = ?";
//        jdbcTemplate.update(updateSql, classDTO.getCode(), classDTO.getName(), classDTO.getTeacher().getId(), classId);
//
//        return classDTO;
//    }
//    @Override
//    @Transactional
//    public void deleteClass(int classId) {
//        String classSql = "SELECT * FROM class WHERE id = ?";
//        List<ClassEntity> classes = jdbcTemplate.query(classSql, new Object[]{classId}, new ClassRowMapper());
//        if (classes.isEmpty()) {
//            throw new IllegalArgumentException("Lớp học không tồn tại");
//        }
//        String studentSql = "SELECT COUNT(*) FROM student WHERE class_id = ?";
//        int studentCount = jdbcTemplate.queryForObject(studentSql, new Object[]{classId}, Integer.class);
//        if (studentCount > 0) {
//            throw new IllegalArgumentException("Lớp học có học sinh, không thể xóa");
//        }
//        String deleteSql = "DELETE FROM class WHERE id = ?";
//        jdbcTemplate.update(deleteSql, classId);
//    }
//
//    @Override
//    public List<ClassEntity> searchClasses(String name, String code, int page, int size) {
//        String searchSql = "SELECT * FROM class WHERE name LIKE ? OR code LIKE ?";
//        return jdbcTemplate.query(searchSql, new Object[]{"%" + name + "%", "%" + code + "%"}, new ClassRowMapper());
//
//    }
//    @Override
//    public List<ClassEntity> getAllClasses(int page, int size) {
//        String sql = "SELECT * FROM class";
//        return jdbcTemplate.query(sql, new ClassRowMapper());
//    }
//    private static class ClassRowMapper implements RowMapper<ClassEntity> {
//        @Override
//        public ClassEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//            ClassEntity classEntity = new ClassEntity();
//            classEntity.setId(rs.getInt("id"));
//            classEntity.setClassCode(rs.getString("code"));
//            classEntity.setName(rs.getString("name"));
//            classEntity.setTeacherId(rs.getInt("teacher_id"));
//            return classEntity;
//        }
//    }
//    private static class TeacherRowMapper implements RowMapper<Teacher> {
//        @Override
//        public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Teacher teacher = new Teacher();
//            teacher.setId(rs.getInt("id"));
//            teacher.setName(rs.getString("name"));
//            return teacher;
//        }
//
//    }
//    public byte[] exportTemplate() throws IOException {
//        // Tải file mẫu từ resource
//        InputStream templateStream = getClass().getResourceAsStream("/templates/template_class.xlsx");
//        if (templateStream == null) {
//            throw new IllegalArgumentException("File mẫu không tồn tại trong thư mục resources/templates.");
//        }
//
//        try (Workbook workbook = new XSSFWorkbook(templateStream);
//             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//
//            List<ClassEntity> classList = jdbcTemplate.query(
//                    "SELECT  class_code, name FROM class",
//                    (rs, rowNum) -> {
//                        ClassEntity classEntity = new ClassEntity();
//                        classEntity.setClassCode(rs.getString("class_code"));
//                        classEntity.setName(rs.getString("name"));
//                        return classEntity;
//                    }
//            );
//
//            List<String> teacherIds = jdbcTemplate.queryForList(
//                    "SELECT teacher_code FROM teacher",
//                    String.class
//            );
//
//            String[] teacherIdArray = teacherIds.toArray(new String[0]);
//            int maxRow = classList.size();
//            CellRangeAddressList addressList = new CellRangeAddressList(1, maxRow, 3, 3);
//            XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
//            DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(teacherIdArray);
//            DataValidation validation = validationHelper.createValidation(constraint, addressList);
//            validation.setSuppressDropDownArrow(true);
//            validation.setShowErrorBox(true);
//            sheet.addValidationData(validation);
//
//            // Điền thông tin các lớp vào sheet
//            int rowNum = 1;
//            for (ClassEntity classEntity : classList) {
//                Row row = sheet.getRow(rowNum);
//                if (row == null) {
//                    row = sheet.createRow(rowNum);
//                }
//                row.createCell(1).setCellValue(classEntity.getClassCode()); // Class Code
//                row.createCell(2).setCellValue(classEntity.getName()); // Class Name
//                row.createCell(3).setCellValue(""); // Placeholder for Teacher Code
//                rowNum++;
//            }
//            workbook.write(out);
//            return out.toByteArray();
//        }

}
