package fx.github.greys.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class GreysResponse<T> {

    private String code;

    private T result;

    private String message;

    public GreysResponse(String code, T result) {
        this.code = code;
        this.result = result;
    }

    public GreysResponse(String code, T result, String message) {
        this.code = code;
        this.result = result;
        this.message = message;
    }

    public GreysResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public GreysResponse(String code) {
        this.code = code;
    }

    private static final String SUCCESS = "0";
    private static final String FAIL = "1";
    private static final String TIMEOUT = "10042";


    public static <T> GreysResponse<T> createSuccess(String message) {
        return new GreysResponse(SUCCESS, message);
    }

    public static <T> GreysResponse<T> createSuccess(T result, String message) {
        return new GreysResponse(SUCCESS, result, message);
    }

    public static <T> GreysResponse<T> createSuccess() {
        return new GreysResponse(SUCCESS);
    }

    public static <T> GreysResponse<T> createError(String message) {
        return new GreysResponse(FAIL, message);
    }

    public static <T> GreysResponse<T> createError() {
        return new GreysResponse(FAIL);
    }

    public static <T> GreysResponse<T> createTimeout() {
        return new GreysResponse(TIMEOUT);
    }
}
