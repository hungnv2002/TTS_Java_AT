package WM.dto;



public class StudentInforCommon {
    private int id;
    private String studentCode;

    private String name;

    private int  classId;

    public StudentInforCommon( String studentCode, String name, int classId) {
        this.studentCode = studentCode;
        this.name = name;
        this.classId = classId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
