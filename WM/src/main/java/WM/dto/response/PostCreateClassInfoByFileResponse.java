package WM.dto.response;

import WM.dto.FileDTOCommon;

import java.util.List;

public class PostCreateClassInfoByFileResponse extends FileDTOCommon {
    private List<String> errorMessages;
    private int successCount;

    public PostCreateClassInfoByFileResponse() {
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
