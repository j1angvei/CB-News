package cn.j1angvei.cnbetareader.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wayne on 2016/8/9.
 */
public class CommentResponse extends BaseResponse {
    @SerializedName(value = "message", alternate = {"error"})
    private String info;
}
