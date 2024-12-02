package com.ms19.jourenal_apk.Response;

public class Response {

    private int status; // Status code (number)
    private boolean success; // Success (boolean)
    private String message; // Message (string)
    private String error;
    private Object data; 

    // Constructor
    public Response(int status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    // Constructor for failure response
    public Response(int status, boolean success, String message, String error, Object data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.error = error;
        this.data= data;
    }
    public Response(int status, boolean success, String message, String error) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.error = error;
    
    }

    // Getter and Setter methods
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
