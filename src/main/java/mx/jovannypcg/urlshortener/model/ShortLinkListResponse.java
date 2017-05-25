package mx.jovannypcg.urlshortener.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ShortLinkListResponse {
    private String error;
    private List<ShortLink> data;
    private Integer status;

    public ShortLinkListResponse() {}
    public ShortLinkListResponse(List<ShortLink> data) {
        this.data = data;
        this.status = HttpStatus.OK.value();
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ShortLink> getData() {
        return data;
    }

    public void setData(List<ShortLink> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
