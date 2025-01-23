package ImportExportExcel.dto.request;

import jakarta.validation.constraints.NotBlank;

public class StudentRequest {
    @NotBlank(message = "Student code is required")
    private String studentCode;

    @NotBlank(message = "Student name is required")
    private String name;

    @NotBlank(message = "Class code is required")
    private String classCode;

    // Getters and Setters
}

