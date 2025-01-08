package menu.service;
import menu.dto.MenuNode;
import menu.entity.Menu;
import menu.repository.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepo menuRepository;

    public List<MenuNode> getMenu() {
        List<Menu> allMenus = menuRepository.findAll();
        return build(null, allMenus, 1);
    }

    private List<MenuNode> build(Integer parentId, List<Menu> allMenus, int level) {
        List<MenuNode> result = new ArrayList<>();
        for (Menu menu : allMenus) {
            if ((parentId == null && menu.getParent() == null) ||
                    (menu.getParent() != null && menu.getParent().getId().equals(parentId))) {
                MenuNode node = new MenuNode(menu, level);
                node.setChildren(build(menu.getId(), allMenus, level + 1));
                result.add(node);
            }
        }
        result.sort(Comparator.comparing(MenuNode::getOrder));
        return result;
    }
}

