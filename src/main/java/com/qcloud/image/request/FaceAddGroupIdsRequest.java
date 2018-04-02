package com.qcloud.image.request;

public class FaceAddGroupIdsRequest extends AbstractBaseRequest {

    private final String mPerson_id;
    private String[] mGroup_ids;
    
    private String mSession_id;

    public FaceAddGroupIdsRequest(String bucketName, String person_id, String... group_ids) {
        super(bucketName);
        mPerson_id = person_id;
        mGroup_ids = group_ids;
    }

    public String getSession_id() {
        return mSession_id;
    }

    public String getPerson_id() {
        return mPerson_id;
    }

    public String[] getGroup_ids() {
        return mGroup_ids;
    }
}
