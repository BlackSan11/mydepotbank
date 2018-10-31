package ru.mydepotbank.accoperator.responses;

public class CustomResponse {
    public static enum statuses { OK, ERR }
    private statuses status;
    private String message;
    private String data;

    public CustomResponse() {
        this.status = statuses.OK;
    }

    public CustomResponse(statuses status, String data) {
        this.status = status;
        this.data = data;
    }

    public CustomResponse(String message) {
        this.status = statuses.ERR;
        this.message = message;
    }

    public statuses getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "CustomResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
