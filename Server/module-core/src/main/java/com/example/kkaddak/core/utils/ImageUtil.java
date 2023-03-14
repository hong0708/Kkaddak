package com.example.kkaddak.core.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Component
public class ImageUtil {
    private static final Logger logger = LogManager.getLogger(ImageUtil.class);
    private final String uploadImagePath;
    private String fileName = "";
    private String uuid = "";
    private String childPath = "";

    public ImageUtil(@Value("${custom.path.upload-images}") String uploadImagePath) {
        this.uploadImagePath = uploadImagePath;
    }

    public String uploadImage(MultipartFile multipartFile, String table) throws Exception {

        logger.info("uploadImagePath :" + uploadImagePath);
        String imagePath = "";

        if (multipartFile.getSize() == 0){
            return imagePath;
        }

        checkImageType(multipartFile);

        String originalFileName = multipartFile.getOriginalFilename();
        fileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
        this.uuid = UUID.randomUUID().toString();
        String uploadFileName = uuid + "_" + fileName;
        File folder = new File(uploadImagePath, File.separator + table);

        childPath = File.separator + table + File.separator + uploadFileName;
        try {

            if (!folder.exists()) {
                folder.mkdirs();
            }
        } catch (SecurityException e) {
            throw new SecurityException("이미지 업로드 폴더 생성 오류");
        }
        File saveFile = null;
        try {
            saveFile = new File(uploadImagePath, childPath);
        } catch (NullPointerException e) {
            throw new NullPointerException("child 파일 생성 불가");
        }

        logger.info("originalFileName : " + originalFileName);
        logger.info("saveFile : " + saveFile);

        try {
            multipartFile.transferTo(saveFile);
        }
        catch(IOException e) {
            logger.info("IOException : 이미지 저장 과정에서 에러가 발생했습니다.");
            throw new IOException(e);
        }
        catch(IllegalStateException e) {
            logger.info("IllegalStateException" + e.getMessage());
            throw new IllegalStateException("the file has already been moved in the filesystem and is not available anymore for another transfer");
        }

        return childPath;
    }

    public boolean removeImage(String imagePath){

        File file = new File(uploadImagePath + imagePath);
        try {
            file.delete();
        }
        catch (SecurityException e) {
            logger.info("removeImage : the calling thread does not have permission to delete the file");
            throw new SecurityException(e.getMessage());
        }
        catch (NullPointerException e) {
            logger.info("removeImage : file argument is null");
            throw new NullPointerException(e.getMessage());
        }
        return true;
    }

    private boolean checkImageType(MultipartFile file) throws Exception {
        try {
            String contentType = file.getContentType();
            return contentType.startsWith("image");
        } catch(Exception e) {
            throw new IllegalArgumentException("파일의 타입이 올바르지 않습니다.");
        }
    }
}