package com.finshine.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table EPHOTO.
 */
public class EPhoto implements java.io.Serializable {

    private Long id;
    private Long photoId;
    private String mimeType;
    private byte[] photoData;

    public EPhoto() {
    }

    public EPhoto(Long id) {
        this.id = id;
    }

    public EPhoto(Long id, Long photoId, String mimeType, byte[] photoData) {
        this.id = id;
        this.photoId = photoId;
        this.mimeType = mimeType;
        this.photoData = photoData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

}
