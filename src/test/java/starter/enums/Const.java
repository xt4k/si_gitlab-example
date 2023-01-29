package starter.enums;

public enum Const {

    ACCEPT_HEADER("Accept", "application/vnd.heroku+json; version=3"),
    AUTH_TOKEN("Authorization", "Bearer 1b0578a4-1472-46b1-b170-bd5b4cd0d8ac"),
    BASE_PATH("","api"),

    DETAIL_ERROR("detail.error","true"),
    UNAUTHORIZED("message","There were no credentials in your `Authorization` header. Try `Authorization: Bearer <OAuth access token>` or `Authorization: Basic <base64-encoded email + \":\" + password>`."),

    USER_NAME("name", "Yuriy");

    private String field;
    private String value;

    Const(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getField() {
        return field;
    }
}
