package fx.github.greys.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class GreysResponse<T> {

    private String code;

    private T data;

    private String message;

    public GreysResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public GreysResponse(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public GreysResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public GreysResponse(String code) {
        this.code = code;
    }

    private static final String SUCCESS = "200";

    private static final String FAIL = "400";

    public static <T> GreysResponse<T> createSuccess(String message) {
        return new GreysResponse(SUCCESS, message);
    }

    public static <T> GreysResponse<T> createSuccess(T data, String message) {
        return new GreysResponse(SUCCESS, data, message);
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
}
