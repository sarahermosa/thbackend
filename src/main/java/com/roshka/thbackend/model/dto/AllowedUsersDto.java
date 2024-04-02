package com.roshka.thbackend.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class AllowedUsersDto implements Serializable {

    private Integer id_user;

    @NotEmpty
    private String email;

}
