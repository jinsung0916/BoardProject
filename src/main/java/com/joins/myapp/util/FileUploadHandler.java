package com.joins.myapp.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.joins.myapp.domain.FileDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUploadHandler {

    /**
     * 1. 개요:
     * 2. 처리내용: 업로드한 파일 내용이 존재하는지 확인한다.
     * 3. 입력 Data: 파일 리스트
     * 4. 출력 Data: 파일이 존재하면 true, 존재하지 않으면 false를 반환한다.
     */
    public static boolean isEmpty(MultipartFile[] uploadFile) {
	return uploadFile[0].getSize() == 0;
    }

    /**
     * 1. 개요:
     * 2. 처리내용: 업로드한 파일을 파일시스템에 저장한다.
     * 3. 입력 Data: 파일 리스트, 파일을 저장할 루트 경로
     * 4. 출력 Data: 파일 정보 리스트
     */
    public static List<FileDTO> uploadFile(MultipartFile[] uploadFile, String baseUploadFolder, long no) {
	List<FileDTO> files = new ArrayList<FileDTO>();
	File uploadFolder = getPath(baseUploadFolder, no);
	for (MultipartFile multipartFile : uploadFile) {
	    // 업로드할 개별 파일의 경로와 이름을 지정하고 파일시스템에 저장한다.
	    log.info("upload file...");
	    log.info("Upload File Name: " + multipartFile.getOriginalFilename());
	    log.info("Upload File Size: " + multipartFile.getSize());
	    
	    // 파일이름에서 확장자를 분리한다.
	    StringTokenizer strtok = new StringTokenizer(getFileName(multipartFile), ".");
	    String fileName = strtok.nextToken();
	    String extension = strtok.nextToken(); 
	    
	    // 파일시스템에 저장될 실제 파일 이름을 생성한다.
	    // TODO 리펙토링
	    String actualFileName = fileName;
	    File saveFile = new File(uploadFolder, actualFileName + "." + extension);
	    int cnt = 1;
	    while(saveFile.exists()){
		// 이름이 중복되는 파일이 존재할 경우 이름을 변경한다.
		actualFileName = fileName + "_" + (cnt++);
		saveFile = new File(uploadFolder, actualFileName + "." + extension);
	    } 
	    
	    try {
		// 파일을 저장하고 파일 정보 객체를 생성한다.
		multipartFile.transferTo(saveFile);
		FileDTO file = new FileDTO();
		file.setUuid(UUID.randomUUID().toString());
		file.setFileName(actualFileName + "." + extension);
		file.setFilePath(uploadFolder.getAbsolutePath());
		files.add(file);
	    } catch (Exception e) {
		log.error(e.getMessage());
	    }
	}
	return files;
    }

    /**
     * 1. 개요:
     * 2. 처리내용: 저장할 세부 경로를 생성하고, 존재하지 않는 디렉토리일 경우 새로 생성한다.  
     * 3. 입력 Data: 파일을 저장할 루트 경로, 게시글 번호
     * 4. 출력 Data: 파일 저장 경로 
     */
    private static File getPath(String baseUploadFolder, long no) {
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	Date date = new Date();
//	String path = sdf.format(date).replace("-", File.separator);
	File uploadFolder = new File(baseUploadFolder, String.valueOf(no));
	
	if (!uploadFolder.exists()) {
	    // 경로가 존재하지 않을 경우 새로운 디렉토리를 생성한다.
	    uploadFolder.mkdirs();
	}

	return uploadFolder;
    }

    /**
     * 1. 개요:
     * 2. 처리내용: 파일 이름을 반환한다(브라우저 별 예외처리).
     * 3. 입력 Data: multipartFile
     * 4. 출력 Data: 파일이름
     */
    private static String getFileName(MultipartFile multipartFile) {
	String fileName = multipartFile.getOriginalFilename();
	// for IE
	fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
	return fileName;
    }
}
