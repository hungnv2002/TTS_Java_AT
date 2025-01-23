package ImportExportExcel.repository;

import ImportExportExcel.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByTeacherCode(String teacherCode);

    boolean existsByTeacherCode(String teacherCode);

}
