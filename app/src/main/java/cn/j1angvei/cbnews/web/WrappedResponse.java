package cn.j1angvei.cbnews.web;

/**
 * Created by Wayne on 2016/6/13.
 */

public class WrappedResponse<T> extends BaseResponse {
    private CnbetaResult<T> result;

    public CnbetaResult<T> getResult() {
        return result;
    }

    public void setResult(CnbetaResult<T> result) {
        this.result = result;
    }

}
