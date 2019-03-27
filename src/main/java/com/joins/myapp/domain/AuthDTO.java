package com.joins.myapp.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("auth")
public class AuthDTO {
    private String userid;
    private String auth;
}
