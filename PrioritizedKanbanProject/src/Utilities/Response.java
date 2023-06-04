package Utilities;

public class Response {
    private final String errorMessage;

    public Response() {
        errorMessage = null;
    }

    public Response(String msg) {
        this.errorMessage = msg;
    }

    public boolean errorOccurred() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}