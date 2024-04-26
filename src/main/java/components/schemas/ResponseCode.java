package components.schemas;

public enum ResponseCode {
    APPROVED("APPROVED"),
    DECLINED("DECLINED");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
