package school.dto;

import java.util.List;

public class ImportException extends RuntimeException {
    private List<ImportError> errors;

    public ImportException(List<ImportError> errors) {
        this.errors = errors;
    }

    public List<ImportError> getErrors() {
        return errors;
    }
}
