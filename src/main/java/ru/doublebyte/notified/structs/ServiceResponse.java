package ru.doublebyte.notified.structs;

/**
 * Response from service
 */
public class ServiceResponse {

    private boolean ok = true;
    private String message = null;

    ///////////////////////////////////////////////////////////////////////////

    public ServiceResponse() {

    }

    public ServiceResponse(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("ServiceResponse{ok=%s, message='%s'}", ok, message);
    }

    ///////////////////////////////////////////////////////////////////////////

    public static ServiceResponse ok() {
        return new ServiceResponse(true, "ok");
    }

    public static ServiceResponse error(String message) {
        return new ServiceResponse(false, message);
    }

    ///////////////////////////////////////////////////////////////////////////

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
