package user.models;

import base.models.ApiResponse;

public class UserDataResponse extends ApiResponse {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
