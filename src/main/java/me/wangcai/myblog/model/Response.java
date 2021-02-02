package me.wangcai.myblog.model;

public class Response {

    private String message;
    private boolean success;

    public Response(String message, boolean success) {
        this.message = message;
        this.success = success;
    }


}
