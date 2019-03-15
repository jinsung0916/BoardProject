package com.joins.myapp.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
public class DataSourceTests {
	@Autowired
	private DataSource dataSource;

	@Test
	public void testConnectioin() {

		try (Connection conn = dataSource.getConnection()) {
			log.info("dataSource succesfully created");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
