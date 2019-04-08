package com.joins.myapp.domain;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("member")
public class MemberDTO {
    private String userid;
    private String userpw;
    private String username;
    private boolean enabled;

    private Date regDate;
    private Date updateDate;
    private List<AuthDTO> authList;
}
