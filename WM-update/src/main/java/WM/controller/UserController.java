package WM.controller;

import WM.core.BaseResponse;
import WM.dto.request.PostRegisterUserInfoRequestBody;
import WM.dto.response.PostRegisterUserInforResponseBody;
import WM.exception.BadRequestException;
import WM.service.impl.UserServiceImpl;
import WM.util.Constain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public BaseResponse<?>postRegisterUserInfo(@Valid @RequestBody PostRegisterUserInfoRequestBody requestBody, HttpServletRequest httpServletRequest, BindingResult result) throws Exception, BadRequestException{
        if(result.hasErrors()){
            throw  new BadRequestException(HttpStatus.BAD_REQUEST.value(), Constain.USER.LIST_ERRORS);
        }
        PostRegisterUserInforResponseBody responseBody= userService.postRegisterUserInfo(requestBody,httpServletRequest);
        return new BaseResponse<>(responseBody);
    }
}
