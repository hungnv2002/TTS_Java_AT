package menu.dto;
import menu.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuNode {
    private Integer id;
    private String name;
    private String url;
    private Integer order;
    private int level;
    private List<MenuNode> children = new ArrayList<>();

    public MenuNode(Menu menu, int level) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.url = menu.getUrl();
        this.order = menu.getOrder();
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Integer getOrder() {
        return order;
    }

    public int getLevel() {
        return level;
    }

    public List<MenuNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }
}
