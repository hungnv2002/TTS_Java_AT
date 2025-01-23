package school.service.impl;

import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import school.dto.TeacherDTO;
import school.service.TeacherService;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void deleteTeacher(int teacherId) {
        String sql = "DELETE FROM teacher WHERE id = ?";
        jdbcTemplate.update(sql, teacherId);
    }

    @Override
    public Page<TeacherDTO> searchTeacher(String name, int page, int size) {
        String sql = "SELECT * FROM teacher WHERE name LIKE ?";
        return (Page<TeacherDTO>) jdbcTemplate.query(sql, new Object[]{"%" + name + "%"}, new TeacherRowMapper());
    }

    @Override
    public List<TeacherDTO> getAllTeachers(int page, int size) {
        String sql = "SELECT * FROM teacher";
        return jdbcTemplate.query(sql, new TeacherRowMapper());
    }

    @Override
    public List<String> importTeachersFromExcel(MultipartFile file) throws IOException {
        List<String> errorMessages = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        int currentRowIndex = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            currentRowIndex++;
            if (currentRowIndex < 3) {
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
            Integer countCode = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM teacher WHERE teacher_code = ?", Integer.class, teacherCode);
            if (countCode != null && countCode > 0) {
                errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": Mã giáo viên '" + teacherCode + "' đã tồn tại.");
                continue;
            }
            Cell nameCell = row.getCell(2);
            if (nameCell == null || nameCell.getStringCellValue().trim().isEmpty()) {
                errorMessages.add("Dòng " + (row.getRowNum() + 1) + ": Tên giáo viên không được để trống.");
                continue;
            }
            String name = nameCell.getStringCellValue().trim();
            jdbcTemplate.update(
                    "INSERT INTO teacher (teacher_code, name) VALUES (?, ?)",
                    teacherCode, name);
        }
        workbook.close();
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
    @Transactional
    public TeacherDTO updateTeacher(TeacherDTO teacherDTO, int teacherId) {
        String sql = "UPDATE teacher SET name = ? and SET teacher_code=?WHERE id = ?";
        jdbcTemplate.update(sql,teacherDTO.getTeacherCode(), teacherDTO.getName(), teacherId);
        return teacherDTO;
    }
private static class TeacherRowMapper implements RowMapper<TeacherDTO> {
        @Override
        public TeacherDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setId(rs.getInt("id"));
            teacherDTO.setName(rs.getString("name"));
            return teacherDTO;
        }
    }

}
