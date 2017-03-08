package com.qcloud.image.sign;

/**
 * @author chengwu
 * 鉴权信息, 包括appId, 密钥对
 */
public class Credentials {
    private final int appId;
    private final String secretId;
    private final String secretKey;

    public Credentials(int appId, String secretId, String secretKey) {
        super();
        this.appId = appId;
        this.secretId = secretId;
        this.secretKey = secretKey;
    }

    public int getAppId() {
        return appId;
    }

    public String getSecretId() {
        return secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

}
