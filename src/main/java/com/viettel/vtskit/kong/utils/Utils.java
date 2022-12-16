package com.viettel.vtskit.kong.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.apache.commons.io.IOUtils;



import java.io.*;

public class Utils {
    private static Logger LOGGER = LoggerFactory.getLogger(Utils.class);
    public static File createTempFile(String extension) throws IOException {
        return File.createTempFile(String.valueOf(System.currentTimeMillis()), extension);
    }
    public static String readFileToStringFromResource(String resourcePath) throws UnsupportedEncodingException {
        byte[] k8sConfig = readFileToByteFromResource(resourcePath);
        return new String(k8sConfig, "UTF-8");
    }
    public static byte[] readFileToByteFromResource(String fileName) {
        try {
            Resource resource = new ClassPathResource(fileName);
            InputStream input = resource.getInputStream();
            return IOUtils.toByteArray(input);
        } catch (IOException var3) {
            logException(LOGGER, var3);
            return new byte[0];
        }
    }

    public static void writeStringToFile(File file, String content){
        try {
            FileUtils.writeStringToFile(file, content, "UTF-8");
        } catch (IOException e) {
            logException(LOGGER, e);
        }
    }
    public static void deleteFile(File file){
        if(!file.delete()){
            logInfo(LOGGER, "Can not delete temp file");
        }
    }


    public static void logException(Logger logger, Exception exception) {
        logger.error(String.format("[EXCEPTION] Message: %s", exception.getMessage()), exception);
    }
    public static void logInfo(Logger logger, String message) {
        logger.info(String.format("[INFO] Message: %s", message));
    }
    public static void skipException(Exception exception) {
    }


}
