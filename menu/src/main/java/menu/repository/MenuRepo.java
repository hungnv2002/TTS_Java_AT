package menu.repository;

import menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu, Integer> {


}
