package com.roshka.thbackend.model.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    @Setter
    @Getter
    private Integer idUsuario;
    @Setter
    @Getter
    private String email;
    @Getter
    private List<String> roles;

    public JwtResponse(String accessToken, Integer id, String email, List<String> roles) {
        this.token = accessToken;
        this.idUsuario = id;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

}
