package uz.jl.vo.http;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Response<T> {
    private final T data;
    private Integer status;
    public Response(T data) {
        this.data = data;
    }
    public Response(T data, Integer status) {
        this.data = data;
        this.status = status;
    }
}
