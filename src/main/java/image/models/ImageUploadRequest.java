package image.models;

public class ImageUploadRequest {
    public ImageUploadRequest(String userId, String imageBase64) {
        this.userId = userId;
        this.imageBase64 = imageBase64;
    }

    private String userId;
    private String imageBase64;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
