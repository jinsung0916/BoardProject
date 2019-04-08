package com.joins.myapp.persistence;

import com.joins.myapp.domain.MemberDTO;

public interface MemberMapper {

    public MemberDTO findByID(String userid);
}
