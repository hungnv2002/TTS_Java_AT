package menu.controller;

import menu.dto.MenuNode;
import menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/")
    public List<MenuNode> getMenu() {
        return menuService.getMenu();
    }
}
