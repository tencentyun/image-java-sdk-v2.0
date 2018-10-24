package com.qcloud.image.common_utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author chengwu 封装了常用的MD5、SHA1、HmacSha1函数
 */
public class CommonCodecUtils {

	private static final String HMAC_SHA1 = "HmacSHA1";

	/**
	 * 对二进制数据进行BASE64编码
	 * 
	 * @param binaryData
	 *            二进制数据
	 * @return 编码后的字符串
	 */
	public static String Base64Encode(byte[] binaryData) {
		String encodedstr =new String(Base64.encodeBase64(binaryData));
		return encodedstr;
	}

	/**
	 * 计算数据的Hmac值
	 * 
	 * @param binaryData
	 *            二进制数据
	 * @param key
	 *            秘钥
	 * @return 加密后的hmacsha1值
     * @throws java.lang.Exception 异常
	 */
	public static byte[] HmacSha1(byte[] binaryData, String key) throws Exception {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1);
			mac.init(secretKey);
			byte[] HmacSha1Digest = mac.doFinal(binaryData);
			return HmacSha1Digest;
	}

	/**
	 * 计算数据的Hmac值
	 * 
	 * @param plainText
	 *            文本数据
	 * @param key
	 *            秘钥
	 * @return 加密后的hmacsha1值
     * @throws java.lang.Exception 异常
	 */
	public static byte[] HmacSha1(String plainText, String key) throws Exception {
		return HmacSha1(plainText.getBytes(), key);
	}
}
