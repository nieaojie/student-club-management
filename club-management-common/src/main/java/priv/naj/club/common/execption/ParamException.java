package priv.naj.club.common.execption;

public class ParamException extends RuntimeException {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ParamException(String code) {
        this.code = code;
    }

    public ParamException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ParamException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ParamException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public ParamException(String message, Throwable cause, boolean enableSuppression,
                          boolean writableStackTrace,
                          String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
