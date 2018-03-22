package com.qcloud.image.common_utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author chengwu 封装了常用的MD5、SHA1、HmacSha1函数
 */
public class CommonCodecUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CommonCodecUtils.class);

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
	 * 获取整个文件的SHA1
	 * 
         * @param filePath 文件路径
	 * @return 文件对应的SHA1值
	 * @throws Exception 异常
	 */
	public static String getEntireFileSha1(String filePath) throws Exception {
		InputStream fileInputStream = null;
		try {
			fileInputStream = CommonFileUtils.getFileInputStream(filePath);
			String sha1Digest = DigestUtils.sha1Hex(fileInputStream);
			return sha1Digest;
		} catch (Exception e) {
			String errMsg = "getFileSha1 occur a exception, file:" + filePath + ", exception:" + e.toString();
			LOG.error(errMsg);
			throw new Exception(errMsg);
		} finally {
			try {
				CommonFileUtils.closeFileStream(fileInputStream, filePath);
			} catch (Exception e) {
				throw e;
			}
		}
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
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1);
			mac.init(secretKey);
			byte[] HmacSha1Digest = mac.doFinal(binaryData);
			return HmacSha1Digest;
			
		} catch (NoSuchAlgorithmException e) {
			LOG.error("mac not find algorithm {}", HMAC_SHA1);
			throw e;
		} catch (InvalidKeyException e) {
			LOG.error("mac init key {} occur a error {}", key, e.toString());
			throw e;
		} catch (IllegalStateException e) {
			LOG.error("mac.doFinal occur a error {}", e.toString());
			throw e;
		}
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
