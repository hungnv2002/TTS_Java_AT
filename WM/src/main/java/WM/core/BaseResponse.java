package WM.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private int status;
    private String errorCode;
    private String message;
    private Object data;

    public BaseResponse(String s) {
        this.status = 200;
        this.errorCode = "noError";
        this.message = "success";
        this.data = data;
    }

    public BaseResponse(T data) {
        this.status = 200;
        this.errorCode = "noError";
        this.message = "success";
        this.data = data;
    }

    public BaseResponse(List<T> data) {
        this.status = 200;
        this.errorCode = "noError";
        this.message = "success";
        this.data = data;
    }

    public BaseResponse(Page<T> data) {
        this.status = 200;
        this.errorCode = "noError";
        this.message = "success";
        this.data = new BasePagination<T>(data);
    }

}
