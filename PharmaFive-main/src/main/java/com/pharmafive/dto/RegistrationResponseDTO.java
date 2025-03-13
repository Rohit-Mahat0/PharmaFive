package com.pharmafive.dto;

public class RegistrationResponseDTO {

    private boolean success;
    private String message;
    private Object data;  // Could also be a custom type or Registration object

    public RegistrationResponseDTO() {
    }

    public RegistrationResponseDTO(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
