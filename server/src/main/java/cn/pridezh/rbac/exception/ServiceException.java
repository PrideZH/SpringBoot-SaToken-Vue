package cn.pridezh.rbac.exception;

import cn.pridezh.rbac.domain.common.ResultCode;

/**
 * 业务异常
 * @author PrideZH
 * @since 2022/8/3 19:06
 */
public class ServiceException extends RuntimeException {

    private final int code;
    private final String message;

    public ServiceException(int code, String message) {
        // 不进行栈追踪
        super(message, null, false, false);

        this.code = code;
        this.message = message;
    }

    public ServiceException(ResultCode resultCode) {
        // 不进行栈追踪
        super(resultCode.message(), null, false, false);

        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public ServiceException(ResultCode resultCode, String message) {
        // 不进行栈追踪
        super(resultCode.message(), null, false, false);

        this.code = resultCode.code();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}