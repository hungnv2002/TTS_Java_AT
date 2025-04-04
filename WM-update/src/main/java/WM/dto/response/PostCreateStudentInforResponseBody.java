package WM.dto.response;

import WM.dto.FileDTOCommon;
import WM.dto.StudentInforCommon;

import java.util.List;

public class PostCreateStudentInforResponseBody extends FileDTOCommon {
    private List<String> errorMessages;
    private int successCount;

    public PostCreateStudentInforResponseBody() {
        this.errorMessages = errorMessages;
        this.successCount = successCount;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
}
