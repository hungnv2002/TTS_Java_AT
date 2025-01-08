package school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import school.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    boolean existsByClassEntityId(int classId);
    Page<Student> findByNameContaining(String name, Pageable pageable);
    Page<Student> findAll(Pageable pageable);
}
