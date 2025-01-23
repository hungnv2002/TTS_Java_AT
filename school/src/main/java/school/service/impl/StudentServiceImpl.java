package school.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import school.dto.StudentDTO;
import school.entity.ClassEntity;
import school.service.StudentService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public StudentDTO addStudent(StudentDTO studentDTO) {
        String classSql = "SELECT * FROM class WHERE id = ?";
        List<ClassEntity> classes = jdbcTemplate.query(classSql, new Object[]{studentDTO.getClassId()}, new ClassRowMapper());
        if (classes.isEmpty()) {
            throw new IllegalArgumentException("Class with code " + studentDTO.getClassId() + " not found");
        }
        String studentSql = "INSERT INTO student (name, age, address, email, class_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(studentSql, studentDTO.getName(), studentDTO.getClassId());

        return studentDTO;
    }
    @Override
    @Transactional
    public StudentDTO updateStudent(int studentId, StudentDTO studentDTO) {
        String studentSql = "SELECT * FROM student WHERE id = ?";
        List<StudentDTO> students = jdbcTemplate.query(studentSql, new Object[]{studentId}, new StudentRowMapper());
        if (students.isEmpty()) {
            throw new IllegalArgumentException("Hoc sinh khong ton tai");
        }
        String updateSql = "UPDATE student SET email = ? WHERE id = ?";
        jdbcTemplate.update(updateSql,studentId);

        return studentDTO;
    }
    @Override
    @Transactional
    public void deleteStudent(int studentId) {
        String studentSql = "SELECT * FROM student WHERE id = ?";
        List<StudentDTO> students = jdbcTemplate.query(studentSql, new Object[]{studentId}, new StudentRowMapper());
        if (students.isEmpty()) {
            throw new IllegalArgumentException("Hoc sinh khong ton tai");
        }
        String deleteSql = "DELETE FROM student WHERE id = ?";
        jdbcTemplate.update(deleteSql, studentId);
    }

    @Override
    public List<StudentDTO> searchStudent(String name, int page, int size) {
        String searchSql = "SELECT * FROM student WHERE name LIKE ?";
        return jdbcTemplate.query(searchSql, new Object[]{"%" + name + "%"}, new StudentRowMapper());
    }

    @Override
    public List<StudentDTO> getAllStudents(int page, int size) {
        String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }
    private static class ClassRowMapper implements RowMapper<ClassEntity> {
        @Override
        public ClassEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            ClassEntity classEntity = new ClassEntity();
            classEntity.setId(rs.getInt("id"));
            return classEntity;
        }
    }
    private static class StudentRowMapper implements RowMapper<StudentDTO> {
        @Override
        public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(rs.getInt("id"));
            studentDTO.setName(rs.getString("name"));
            studentDTO.setClassId(rs.getInt("class_id"));
            return studentDTO;
        }
    }
}
