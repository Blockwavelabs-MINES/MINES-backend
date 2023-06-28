package io.propwave.tree.common.dto;

import io.propwave.tree.exception.Error;
import io.propwave.tree.exception.Success;
import lombok.*;

@ToString
@Getter
@RequiredArgsConstructor(access =  AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private T data;

    public static <T> ApiResponse<T> success(Success success) {
        return new ApiResponse<>(success.getCode().value(), success.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(Success success, T data) {
        return new ApiResponse<>(success.getCode().value(), success.getMessage(), data);
    }

    public static <T> ApiResponse<T> error(Error error) {
        return new ApiResponse<>(error.getCode().value(), error.getMessage(), null);
    }
}
