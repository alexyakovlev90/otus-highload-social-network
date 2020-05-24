package ru.otus.highload.util.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class Response<T> extends AbstractResponse {
    private T data;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(Boolean success) {
        this.setResult(success ? ResponseStatus.SUCCESS.getId() : ResponseStatus.FAILURE.getId());
    }

    public Response(Exception e) {
        super(e);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "Response{result='" + this.getResult() + '\'' + ", msg='" + this.getMsg() + '\'' + ", data=" + this.data + '}';
    }
}
