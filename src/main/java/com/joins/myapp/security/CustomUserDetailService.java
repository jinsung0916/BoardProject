package com.joins.myapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.joins.myapp.domain.MemberDTO;
import com.joins.myapp.persistence.MemberMapper;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	MemberDTO member = memberMapper.findByID(username);
	if(member == null) 
	    throw new UsernameNotFoundException(username);
	return new CustomUser(member);
    }

}
