package ImportExportExcel.dto.response;

import java.util.List;

public class ClassResponse {
    private int id;
    private String classCode;
    private String name;
    private TeacherResponse teacher;
    private List<StudentResponse> students;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeacherResponse getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherResponse teacher) {
        this.teacher = teacher;
    }

    public List<StudentResponse> getStudents() {
        return students;
    }

    public void setStudents(List<StudentResponse> students) {
        this.students = students;
    }

    // Getters and Setters
}
