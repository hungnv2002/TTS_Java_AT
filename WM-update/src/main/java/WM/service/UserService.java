package WM.service;

import WM.dto.request.PostRegisterUserInfoRequestBody;
import WM.dto.response.PostRegisterUserInforResponseBody;
import WM.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface UserService {
    PostRegisterUserInforResponseBody postRegisterUserInfo(PostRegisterUserInfoRequestBody requestBody, HttpServletRequest httpServletRequest) throws IOException, BadRequestException;
}
