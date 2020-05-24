package ru.otus.highload.util.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public abstract class AbstractResponse {
    private String result;
    private String msg;

    public AbstractResponse() {
        this.result = ResponseStatus.SUCCESS.getId();
    }

    public AbstractResponse(Exception e) {
        this.result = ResponseStatus.SUCCESS.getId();
        this.result = ResponseStatus.FAILURE.getId();
        this.msg = e.getMessage();
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getSuccess() {
        return ResponseStatus.SUCCESS.getId().equals(this.result);
    }
}
