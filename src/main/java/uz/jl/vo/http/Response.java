package uz.jl.vo.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Response<T> {
    private T body;
    private Integer total;
    private ErrorVO error;
    private boolean ok;

    public Response(T body) {
        this(body, null);
    }

    public Response(T body, Integer total) {
        this.body = body;
        this.total = total;
    }

    public Response(ErrorVO error, boolean ok) {
        this.error = error;
        this.ok = ok;
    }

    public static ErrorVO errorBuilder() {
        return new ErrorVO();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorVO {
        private String friendlyMessage;
        private String developerMessage;
        private int status;

        private Timestamp timestamp;


        public ErrorVO friendlyMessage(String friendlyMessage) {
            this.friendlyMessage = friendlyMessage;
            return this;
        }

        public ErrorVO developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ErrorVO status(int status) {
            this.status = status;
            return this;
        }

        public Response<ErrorVO> build() {
            ErrorVO errorVO = new ErrorVO(
                    this.friendlyMessage,
                    this.developerMessage,
                    this.status,
                    Timestamp.valueOf(LocalDateTime.now()));
            return new Response<>(errorVO, false);
        }
    }
}
