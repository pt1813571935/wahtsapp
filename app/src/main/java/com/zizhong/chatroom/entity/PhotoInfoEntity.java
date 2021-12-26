package com.zizhong.chatroom.entity;

/**
 * FlymeCat保佑，永无BUG
 * Created by DJL on 2021/12/23.
 */

public class PhotoInfoEntity {

    private String imgUrl;
    private boolean isChecked = false;

    public PhotoInfoEntity(String imgUrl, boolean isChecked) {
        this.imgUrl = imgUrl;
        this.isChecked = isChecked;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
