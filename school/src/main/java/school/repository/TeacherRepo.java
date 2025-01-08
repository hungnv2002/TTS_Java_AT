package school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import school.entity.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher,Integer> {
    Page<Teacher> findByNameContaining(String name, Pageable pageable);
    Page<Teacher> findAll(Pageable pageable);
}
