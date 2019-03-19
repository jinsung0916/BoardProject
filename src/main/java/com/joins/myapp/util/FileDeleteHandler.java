package com.joins.myapp.util;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileDeleteHandler {
    public static void deleteFile(String absoluteFilePath) {
	Path file = Paths.get(absoluteFilePath);
	try { 
	    Files.deleteIfExists(file);
	} catch(NoSuchFileException e) { 
	    log.info("No such file/directory '{}' exists.", file.getFileName()); 
	} catch(DirectoryNotEmptyException e) { 
	    log.info("Directory '{}' is not empty.", file.getFileName()); 
	} catch(IOException e) { 
	    log.info("Invalid permissions of deleting '{}'.", file.getFileName()); 
	} 
	log.info("'{}' is successfully deleted.", file.getFileName());   
    }
}
