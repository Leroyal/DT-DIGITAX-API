package com.digitax.constants;

public class HttpStatusCode {

    private final int code;
    private final String message;

    /**
     * Represents an HTTP status with code and message.
     *
     * @param code Numeric value of the HTTP response status.
     * @param message Description of the status.
     */
    private HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }

    /**
     * Retrieve status code association to the error message.
     * @return Status code.
     */
    public int code() {
        return this.code;
    }

    /**
     * 2XX: Success. Indicates that the client's request was successfully received, understood,
     * and accepted.
     */
    public static final HttpStatusCode SUCCESS = new HttpStatusCode(200, "SUCCESS");

    /**
     * 4XX: Client Error. Indicates cases where the client has error.
     */
    public static final HttpStatusCode USERNAME_ALREADY_EXISTS = new HttpStatusCode(400, "Username is already in use.");
    public static final HttpStatusCode EMAIL_ALREADY_EXISTS = new HttpStatusCode(400, "Email is already in use.");
    public static final HttpStatusCode PHONE_NUMBER_ALREADY_EXISTS = new HttpStatusCode(400, "Phone number is already in use.");
    public static final HttpStatusCode WRONG_EMAIL_OR_PASSWORD = new HttpStatusCode(401, "Wrong email or password.");
    public static final HttpStatusCode INVALID_EMAIL_FORMAT = new HttpStatusCode(401, "Invalid email format.");
    public static final HttpStatusCode INVALID_PASSWORD_FORMAT = new HttpStatusCode(401, "Invalid password format.");

    /**
     * 5XX: Server Error. Indicates that the server is aware that it has error or is incapable
     * of performing the request.
     */
    public static final HttpStatusCode INTERNAL_SERVER_ERROR = new HttpStatusCode(500, "FAILURE");
}
