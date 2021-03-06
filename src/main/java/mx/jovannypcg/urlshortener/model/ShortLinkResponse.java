package mx.jovannypcg.urlshortener.model;

import org.springframework.http.HttpStatus;

public class ShortLinkResponse {
    private String error;
    private ShortLink data;
    private Integer status;

    public ShortLinkResponse() {}
    public ShortLinkResponse(ShortLink data) {
        this.data = data;
        this.status = HttpStatus.OK.value();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ShortLink getData() {
        return data;
    }

    public void setData(ShortLink data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
