package WM.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VmException extends Exception {
    private Integer statusCode;
    private String errorCode;
    private String message;
}
