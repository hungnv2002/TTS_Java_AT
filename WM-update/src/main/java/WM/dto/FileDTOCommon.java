package WM.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileDTOCommon {

    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }


}
