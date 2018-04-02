package com.qcloud.image.request;

public class FaceDelGroupIdsRequest extends AbstractBaseRequest {

    private final String mPerson_id;
    private String[] mGrooup_ids;
    
    private String mSession_id;

    public FaceDelGroupIdsRequest(String bucketName, String person_id, String... grooup_ids) {
        super(bucketName);
        mPerson_id = person_id;
        mGrooup_ids = grooup_ids;
    }

    public String getSession_id() {
        return mSession_id;
    }

    public String getPerson_id() {
        return mPerson_id;
    }

    public String[] getGrooup_ids() {
        return mGrooup_ids;
    }
}
