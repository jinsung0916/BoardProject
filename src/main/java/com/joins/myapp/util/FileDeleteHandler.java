package com.joins.myapp.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileDeleteHandler {
    
    /**
     * 1. 개요: 
     * 2. 처리내용: 해당 경로에 존재하는 폴더를 삭제한다.
     * 3. 입력 Data: 폴더의 절대경로
     * 4. 출력 Data: 
     */
    public static void deleteFolder(String absoluteFolderPath) {
	Path folder = Paths.get(absoluteFolderPath);
	try {
	    deleteDirectoryRecursion(folder);
	} catch (IOException e) {
	    log.error("폴더 삭제 실패");
	}
    }
    
    /**
     * 1. 개요: 디렉토리 삭제를 위한 private 매소드
     * 2. 처리내용: 디렉토리를 재귀적으로 순회하며 모든 파일을 삭제하고 최종적으로 해당 디렉토리를 삭제한다.
     * 3. 입력 Data: Path 객체
     * 4. 출력 Data: 
     */
    private static void deleteDirectoryRecursion(Path path) throws IOException {
	if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
	    // 삭제 대상이 디렉토리일 때
	    try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
	      for (Path entry : entries) {
		  // 디렉토리 안에 존재하는 디렉토리와 파일을 순회하며 삭제한다.
		  deleteDirectoryRecursion(entry);
	      }
	    }
	}
	Files.delete(path);
    }

}
