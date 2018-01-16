package com.qcloud.image.sign;

import java.util.Random;

import com.qcloud.image.common_utils.CommonCodecUtils;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.UnknownException;

/**
 * @author chengwu 封装签名类，包括单次，多次以及下载签名
 */
public class Sign {

    /**
     * 返回万象图片的多次有效签名
     *
     * @param cred       包含用户秘钥信息
     * @param bucketName bucket名
     * @param expired    超时时间
     * @return 返回base64编码的字符串
     * @throws AbstractImageException 异常
     */
    public static String appSign(Credentials cred, String bucketName, long expired) throws AbstractImageException {
        return appSign(cred, bucketName, "", expired);
    }

    /**
     * 返回万象图片特定资源的多次有效签名
     *
     * @param cred       包含用户秘钥信息
     * @param bucketName bucket名
     * @param fileid     特定资源（会验证与当前操作的文件路径是否一致）
     * @param expired    超时时间
     * @return 返回base64编码的字符串
     * @throws AbstractImageException 异常
     */
    public static String appSign(Credentials cred, String bucketName, String fileid, long expired) throws AbstractImageException {
        return generateSignature(cred, bucketName, fileid, expired);
    }

    private static String generateSignature(Credentials cred, String bucketName, String fileid, long expired) throws AbstractImageException {
        int appId = cred.getAppId();
        String secretId = cred.getSecretId();
        String secretKey = cred.getSecretKey();
        int rdm = Math.abs(new Random().nextInt());
        long now = System.currentTimeMillis() / 1000;

        String plainText = String.format("a=%d&b=%s&k=%s&t=%d&e=%d&r=%d&f=%s", appId, bucketName, secretId, now, now + expired, rdm, fileid);

        byte[] hmacDigest;
        try {
            hmacDigest = CommonCodecUtils.HmacSha1(plainText, secretKey);
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }
        byte[] signContent = new byte[hmacDigest.length + plainText.getBytes().length];
        System.arraycopy(hmacDigest, 0, signContent, 0, hmacDigest.length);
        System.arraycopy(plainText.getBytes(), 0, signContent, hmacDigest.length, plainText.getBytes().length);

        return CommonCodecUtils.Base64Encode(signContent);
    }
}
