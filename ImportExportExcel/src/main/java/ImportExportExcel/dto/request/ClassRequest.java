package ImportExportExcel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClassRequest {
    @Size(message = "Class code is required")
    @Size(max = 10, message = "Class code must not exceed 10 characters")
    private String classCode;

    @NotBlank(message = "Class name is required")
    @Size(max = 50, message = "Class name must not exceed 50 characters")
    private String name;

    @NotBlank(message = "Teacher code is required")
    private String teacherCode;

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

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }
}

