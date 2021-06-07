package com.springbank.bankacc.cmd.api.dtos;

import com.springbank.bankacc.core.dto.BaseResponse;
import lombok.Builder;

@Builder
public class OpenAccountResponse extends BaseResponse {

    private String id;

    public OpenAccountResponse(String message) {
        super(message);
        this.id = id;
    }

    public OpenAccountResponse(String id, String message) {
        super(message);
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
