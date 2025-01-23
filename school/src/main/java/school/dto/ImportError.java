package school.dto;

public class ImportError {
    private int rowIndex; // Chỉ số hàng trong file Excel
    private String errorMessage; // Thông báo lỗi

    // Constructor, getter và setter
    public ImportError(int rowIndex, String errorMessage) {
        this.rowIndex = rowIndex;
        this.errorMessage = errorMessage;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
