package WM.service;

import WM.dto.request.PostCreateStudentInfoByFileRequestBody;
import WM.dto.response.PostCreateStudentInforResponseBody;
import WM.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface StudentService {
    PostCreateStudentInforResponseBody postCreateStudentInfoByFile(PostCreateStudentInfoByFileRequestBody requestBody, HttpServletRequest httpServletRequest) throws BadRequestException, IOException;

}
