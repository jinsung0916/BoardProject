package com.joins.myapp.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joins.myapp.domain.FileDTO;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
public class FileMapperTest {

    @Autowired
    private FileMapper mapper;

    @Test
    public void testInert() {
	FileDTO file = new FileDTO();
	file.setUuid("1");
	file.setFileName("2");
	file.setFilePath("3");
	file.setBoardNo(1L);
	mapper.insert(file);
    }

    @Test
    public void testDelete() {
	log.info(mapper.delete("1") + "");
    }

    @Test
    public void testFindByUUID() {
	FileDTO file = mapper.findByUUID("7a9cfd5c-fe2f-4260-a9b1-9ef974ce6402");
	log.info(file.toString());
    }
}
