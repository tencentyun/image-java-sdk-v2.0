package com.qcloud.image.request;

import com.qcloud.image.common_utils.CommonParamCheckUtils;
import com.qcloud.image.exception.ParamException;

import java.io.File;

public class FaceMultiIdentifyRequest extends AbstractBaseRequest {

    private boolean isUrl = true;

    private File mImageFile;
    private String mImageUrl;

    private String[] mGroup_ids;

    private String session_id = "";

    public FaceMultiIdentifyRequest(String bucketName, File imageFile, String... group_ids) {
        super(bucketName);
        isUrl = false;
        mImageFile = imageFile;
        mGroup_ids = group_ids;
    }

    public FaceMultiIdentifyRequest(String bucketName, String imageUrl, String... group_ids) {
        super(bucketName);
        isUrl = true;
        mImageUrl = imageUrl;
        mGroup_ids = group_ids;
    }

    public boolean isUrl() {
        return isUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public File getImageFile() {
        return mImageFile;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String[] getGroup_ids() {
        return mGroup_ids;
    }

    @Override
    public void check_param() throws ParamException {
        super.check_param();
        if (isUrl) {
            CommonParamCheckUtils.AssertNotNull("url", mImageUrl);
        } else {
            CommonParamCheckUtils.AssertNotNull("image content", mImageFile);
        }
    }
}
