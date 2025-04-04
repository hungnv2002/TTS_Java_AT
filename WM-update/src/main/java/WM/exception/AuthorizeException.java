package WM.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizeException extends RuntimeException {
    public AuthorizeException(int value, String message) {
        super(message);
    }
}
