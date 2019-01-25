package com.qcloud.image.sign;

/**
 * @author chengwu
 * 鉴权信息, 包括appId, 密钥对
 */
public class Credentials {
    private final String appId;
    private final String secretId;
    private final String secretKey;

    public Credentials(String appId, String secretId, String secretKey) {
        if (appId == null || appId.trim().isEmpty()) {
            throw new NullPointerException("AppId can not be null or empty");
        }
        if (secretId == null || secretId.trim().isEmpty()) {
            throw new NullPointerException("secretId can not be null or empty");
        }
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new NullPointerException("secretKey can not be null or empty");
        }
        this.appId = appId;
        this.secretId = secretId;
        this.secretKey = secretKey;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecretId() {
        return secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

}
