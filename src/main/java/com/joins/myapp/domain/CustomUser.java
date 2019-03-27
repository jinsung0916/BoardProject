package com.joins.myapp.domain;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class CustomUser extends User {
    private MemberDTO member;

    public CustomUser(MemberDTO member) {
	super(member.getUserid(), member.getUserpw(), member.getAuthList().stream()
		.map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
	this.member = member;
    }
}
