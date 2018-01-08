package com.qcloud.image.common_utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author chengwu 
 * 封装了一些常用的文件操作函数
 */
public class CommonFileUtils {

    private static Logger LOG = LoggerFactory.getLogger(CommonFileUtils.class);

    /**
     * 判断指定路径的文件是否有效, 即文件存在，且可读
     * @param filePath 文件路径
     * @return  有效返回true, 否则返回false
     */
    public static boolean isLegalFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return false;
        }
        return true;
    }

    /**
     * 获取文件长度，单位为字节
     * 
     * @param filePath 文件的本地路径
     * @return 文件长度,单位为字节
     * @throws Exception 文件不存在或者是一个目录，则抛出异常
     */
    public static long getFileLength(String filePath) throws Exception {
        File file = new File(filePath);
        return file.length();
    }

    /**
     * 打开对应的文件，并返回文件输入流
     * 
     * @param filePath 文件路径
     * @return 文件输入流
     * @throws FileNotFoundException 如果文件不存在，则抛出异常
     */
    public static FileInputStream getFileInputStream(String filePath) throws Exception {
        FileInputStream localFileInputStream = new FileInputStream(filePath);
        return localFileInputStream;
    }

    /**
     * 关闭对应的文件流
     * 
     * @param inputStream 待关闭的文件流
     * @param filePath 对应的文件名
     */
    public static void closeFileStream(InputStream inputStream, String filePath){
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            LOG.error("close file {} occur an IOExcpetion {}", filePath, e);
        }
    }

     /**
     * 获取小文件的全部内容
     * 
     * @param filePath 文件路径
     * @return 返回文件内容
     * @throws Exception  异常
     */
    public static byte[] getFileContentByte(String filePath) throws Exception {
        int fileLength = ((Long) getFileLength(filePath)).intValue();
        return getFileContentByte(filePath, 0, fileLength);
    }

        /**
     * 获取文件指定块的内容
     * 
     * @param filePath 文件路径
     * @param offset 偏移量，即从哪里开始读取，单位为字节
     * @param length 读取的长度,单位为字节
     * @return 返回读取的内容，实际读取的长度小于等于length
     * @throws Exception 异常
     */
    public static byte[] getFileContentByte(String filePath, long offset, int length) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getFileInputStream(filePath);
            return getFileContentByte(fileInputStream, offset, length);
        } finally {
            closeFileStream(fileInputStream, filePath);
        }
    }

    public static byte[] getFileContentByte(InputStream inputStream, long offset, int length)
            throws Exception {
        if (offset < 0 || length < 0) {
            throw new Exception("getFileContent param error");
        }

        byte[] fileContent = null;
        byte[] tempBuf = new byte[length];

        inputStream.skip(offset);
        int readLen = inputStream.read(tempBuf);
        if (readLen < 0) {
            fileContent = new byte[0];
            return fileContent;
        }
        if (readLen < length) {
            fileContent = new byte[readLen];
            System.arraycopy(tempBuf, 0, fileContent, 0, readLen);
        } else {
            fileContent = tempBuf;
        }
        return fileContent;
    }

    /**
     * 删除文件
     * @param filePath   文件路径
     */
    public static void remove(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }
    
    /**
     * 获取文件上次的修改时间
     * 
     * @param filePath 文件的本地路径
     * @return 文件长度,单位为字节
     * @throws Exception 文件不存在或者是一个目录，则抛出异常
     */
    public static long getFileLastModified(String filePath) throws Exception {
        if (!isLegalFile(filePath)) {
            String errorMsg = filePath + " is not file or not exist or can't be read!";
            LOG.error(errorMsg);
            throw new Exception(errorMsg);
        }
        File file = new File(filePath);
        return file.lastModified();
    }
}
