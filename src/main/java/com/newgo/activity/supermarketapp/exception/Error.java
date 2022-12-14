package com.newgo.activity.supermarketapp.exception;

public class Error {

    private String name;
    private String cause;

    public Error() {

    }
    public Error(String name, String cause) {
        this.name = name;
        this.cause = cause;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
