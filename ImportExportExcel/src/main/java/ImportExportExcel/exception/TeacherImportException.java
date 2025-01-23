package ImportExportExcel.exception;

import java.util.List;

public class TeacherImportException extends RuntimeException {
    private final List<String> errorMessages;

    public TeacherImportException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    @Override
    public String getMessage() {
        return "Errors occurred during teacher import: " + String.join("; ", errorMessages);
    }
}

