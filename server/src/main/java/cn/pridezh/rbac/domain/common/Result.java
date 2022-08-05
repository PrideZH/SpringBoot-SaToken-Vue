package cn.pridezh.rbac.domain.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author PrideZH
 * @since 2022/8/3 19:03
 */
@ApiModel("统一返回数据结构")
@Accessors(chain = true)
@AllArgsConstructor
@Data
public class Result<T> implements Serializable {

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("响应描述")
    private String message;

    @ApiModelProperty("响应数据")
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.OK.code(), ResultCode.OK.message(), data);
    }

    public static Result<Void> fail() {
        return new Result<>(ResultCode.BAD_REQUEST.code(), ResultCode.BAD_REQUEST.message(), null);
    }

    public static Result<Void> fail(ResultCode resultCode) {
        return new Result<>(resultCode.code(), resultCode.message(), null);
    }

    public static <T> Result<T> fail(ResultCode resultCode, T data) {
        return new Result<>(resultCode.code(), resultCode.message(), data);
    }

    public static Result<Void> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }

}