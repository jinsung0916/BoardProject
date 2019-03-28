package com.joins.myapp.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joins.myapp.domain.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml" })
@Slf4j
public class MemberMapperTests {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void testFindById() {
	MemberDTO member = memberMapper.findByID("user20");
	log.info(member.toString());
    }
}
