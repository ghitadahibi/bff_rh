package ma.pca.bff.config;

import ma.pca.starter.web.exception.ExceptionType;

public enum ExampleExceptionType implements ExceptionType {

    EXAMPLE_EXCEPTION("001");

    private final String code;

    ExampleExceptionType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
