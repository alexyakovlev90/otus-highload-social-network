package ru.otus.highload.socialbackend.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class ListResponse<T> extends AbstractResponse {
    public List<T> data = new ArrayList();

    public ListResponse() {
    }

    public ListResponse(T data) {
        this.data = Collections.singletonList(data);
    }

    public ListResponse(Collection<T> data) {
        this.data = new ArrayList(data);
    }

    public ListResponse(T... data) {
        this.data = Arrays.asList(data);
    }

    public ListResponse(Exception e) {
        super(e);
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean add(T elm) {
        return this.data.add(elm);
    }

    public String toString() {
        return "Response{result='" + this.getResult() + '\'' + ", msg='" + this.getMsg() + '\'' + ", data=" + this.data + '}';
    }
}
