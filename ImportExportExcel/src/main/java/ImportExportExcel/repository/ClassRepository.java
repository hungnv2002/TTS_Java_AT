package ImportExportExcel.repository;

import ImportExportExcel.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRepository extends JpaRepository<ClassEntity,Integer> {
    @Query("SELECT c FROM ClassEntity c WHERE c.classCode = :classCode")
    List<ClassEntity> findByClassCode(@Param("classCode") String classCode);


    boolean existsByClassCode(String classCode);
}
