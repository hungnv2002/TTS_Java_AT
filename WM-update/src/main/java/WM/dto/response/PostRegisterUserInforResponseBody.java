package WM.dto.response;

import WM.dto.UserInfoCommon;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRegisterUserInforResponseBody extends UserInfoCommon {
}
