//package WM.repository;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import school.entity.Teacher;
//
//import java.util.List;
//
//public interface TeacherRepo extends JpaRepository<Teacher,Integer> {
//    Page<Teacher> findByNameContaining(String name, Pageable pageable);
//    Page<Teacher> findAll(Pageable pageable);
//    @Query("SELECT t.id FROM Teacher t")
//    List<String> findAllTeacherIds();
//}
