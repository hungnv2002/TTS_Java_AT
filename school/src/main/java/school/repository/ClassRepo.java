package school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import school.entity.ClassEntity;
import school.entity.Teacher;

import java.util.Optional;

public interface ClassRepo extends JpaRepository<ClassEntity, Integer> {

}
