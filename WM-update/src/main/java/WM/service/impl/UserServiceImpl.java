package WM.service.impl;

import WM.dto.UserInfoCommon;
import WM.dto.request.PostRegisterUserInfoRequestBody;
import WM.dto.response.CreateTeacherInforRespone;
import WM.dto.response.PostRegisterUserInforResponseBody;
import WM.exception.BadRequestException;
import WM.service.UserService;
import WM.util.Constain;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

     private final JdbcTemplate jdbcTemplate;
     private final ModelMapper modelMapper;

    public UserServiceImpl(JdbcTemplate jdbcTemplate, ModelMapper modelMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostRegisterUserInforResponseBody postRegisterUserInfo(PostRegisterUserInfoRequestBody requestBody, HttpServletRequest httpServletRequest) throws IOException, BadRequestException {
        Integer countCode = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM user WHERE user_name = ?", Integer.class, requestBody.getUserName());
        if (countCode != null && countCode > 0) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), Constain.USER.USER_USERNAME_EXSIST);
        }
       validateUserInfoFromRequestBody (requestBody);
        String sql = "INSERT INTO user (name, user_name,password) VALUES (?,?,?)";
        jdbcTemplate.update(sql, requestBody.getName(), requestBody.getUserName(),requestBody.getPassword());
        return modelMapper.map(requestBody, PostRegisterUserInforResponseBody.class);
    }
    private <T extends UserInfoCommon> void validateUserInfoFromRequestBody(T request) throws BadRequestException{
        if(StringUtils.isBlank(request.getName())){
            throw  new BadRequestException(HttpStatus.BAD_REQUEST.value(), Constain.USER.USER_NAME_BLANK);
        }
        if(StringUtils.isBlank(request.getUserName())){
            throw  new BadRequestException(HttpStatus.BAD_REQUEST.value(), Constain.USER.USER_USERNAME_BLANK);
        }
        if(StringUtils.isBlank(request.getPassword())){
            throw  new BadRequestException(HttpStatus.BAD_REQUEST.value(), Constain.USER.USER_PASSWORD_BLANK);
        }

    }
}
