package WM.dto;

public class TeacherCommon {
    private int id;

    private String teacherCode;
    private String name;

    public TeacherCommon(String teacherCode, String name) {
        this.teacherCode = teacherCode;
        this.name = name;
    }

    public TeacherCommon(int id, String teacherCode, String name) {
        this.id = id;
        this.teacherCode = teacherCode;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
