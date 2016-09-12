package cn.j1angvei.cbnews.web;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 2016/6/13.
 */

public class ExposedResponse<T> extends BaseResponse {
    private List<T> result;

    public ExposedResponse() {
        result = new ArrayList<>();
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }


}
