package ImportExportExcel.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class TeacherRequest  {
    @Size(max = 10, message = "Teacher code must not exceed 10 characters")
    private String teacherCode;

    @Size(max = 50, message = "Teacher name must not exceed 50 characters")
    private String name;
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

