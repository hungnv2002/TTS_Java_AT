package ImportExportExcel.exception;

import java.util.List;

public class ClassImportException extends RuntimeException {
    private List<String> errorMessages;

    public ClassImportException(List<String> errorMessages) {
        super("Error importing class data");
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}

