package com.qcloud.image.sign;

import com.qcloud.image.common_utils.CommonCodecUtils;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.UnknownException;

import java.util.Random;

/**
 * @author chengwu 封装签名类，包括单次，多次以及下载签名
 */
public class Sign {

	/**
	 * 返回图片识别的签名
	 * 
	 * @param cred
	 *            包含用户秘钥信息
	 * @param bucketName
	 *            bucket名
	 * @param expired
	 *            超时时间
	 * @return 返回base64编码的字符串
	 * @throws AbstractImageException  异常
	 */
        public static String appSign(Credentials cred, String bucketName, long expired) throws AbstractImageException {
            String appId = cred.getAppId();
            String secretId = cred.getSecretId();
            String secretKey = cred.getSecretKey();
            long now = System.currentTimeMillis() / 1000;   
            int rdm = Math.abs(new Random().nextInt());
            String plainText = String.format("a=%s&b=%s&k=%s&t=%d&e=%d", appId, bucketName, secretId, now, now + expired);
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
