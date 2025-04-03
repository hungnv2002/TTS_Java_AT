package WM.exception;

import WM.core.BaseResponse;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class VmExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({VmException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            VmException ex, WebRequest request) {
        log.error(" {} ServiceException e = {} ", getClass().getSimpleName(), ex);
        return new ResponseEntity<>(
                new BaseResponse<String>(ex.getStatusCode(), ex.getErrorCode(), ex.getMessage(), null), new HttpHeaders(), HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        log.error(" {} ServiceException e = {} ", getClass().getSimpleName(), ex);
        return new ResponseEntity<>(
                new BaseResponse<String>(500, ex.getMessage(), ex.getMessage(), null), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({VmListException.class})
    public ResponseEntity<Object> handleValidationException(
            VmListException ex, WebRequest request) {
        log.error(" {} ServiceException e = {} ", getClass().getSimpleName(), ex);
        Map<String, List<String>> errors = new HashMap<>();
        List<String> errorMessageList = new ArrayList<>();
        ex.getErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorDefaultMessage = error.getDefaultMessage();

            errorMessageList.add(StringUtils.isNotEmpty(errorDefaultMessage) ? String.format(errorDefaultMessage, fieldName) : fieldName);
        });
        errors.put("detail", errorMessageList);
        return new ResponseEntity<>(
                new BaseResponse<>(400, ex.getErrorCode(), ex.getMessage(), errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
