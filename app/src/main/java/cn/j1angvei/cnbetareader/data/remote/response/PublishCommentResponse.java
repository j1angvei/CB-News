package cn.j1angvei.cnbetareader.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wayne on 2016/8/9.
 */
public class PublishCommentResponse extends BaseResponse {
    @SerializedName(value = "message", alternate = {"error"})
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}