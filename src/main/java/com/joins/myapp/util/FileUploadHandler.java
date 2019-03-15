package com.joins.myapp.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public static List<FileDTO> uploadFile(MultipartFile[] uploadFile, String baseUploadFolder) {
	List<FileDTO> files = new ArrayList<FileDTO>();
	File uploadFolder = getPath(baseUploadFolder);
	for (MultipartFile multipartFile : uploadFile) {
	    log.info("upload file...");
	    log.info("Upload File Name: " + multipartFile.getOriginalFilename());
	    log.info("Upload File Size: " + multipartFile.getSize());

	    String fileName = getFileName(multipartFile);
	    UUID uuid = UUID.randomUUID();
	    String fileNameWithUUID = uuid + "_" + fileName;

	    File saveFile = new File(uploadFolder, fileNameWithUUID);

	    try {
		multipartFile.transferTo(saveFile);
		FileDTO file = new FileDTO();
		file.setUuid(uuid.toString());
		file.setFileName(fileName);
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
     * 3. 입력 Data: 파일을 저장할 루트 경로
     * 4. 출력 Data: 파일 저장 경로 
     */
    private static File getPath(String baseUploadFolder) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	String path = sdf.format(date).replace("-", File.separator);

	File uploadFolder = new File(baseUploadFolder, path);

	if (!uploadFolder.exists()) {
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
