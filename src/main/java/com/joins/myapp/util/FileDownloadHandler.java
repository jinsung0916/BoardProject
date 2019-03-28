package com.joins.myapp.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileDownloadHandler {
    /**
     * 1. 개요: 
     * 2. 처리내용: 파일 리소스를 반환한다.
     * 3. 입력 Data: 파일의 절대경로
     * 4. 출력 Data: 파일 리소스
     * @throws Exception 
     */
    public static Resource downloadFile(String absoluteFilePath) {
	Resource resource = new FileSystemResource(absoluteFilePath);

	if (!resource.exists()) {
	    throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}

	log.info("download file...");
	log.info("resource: " + resource);

	return resource;
    }
}
