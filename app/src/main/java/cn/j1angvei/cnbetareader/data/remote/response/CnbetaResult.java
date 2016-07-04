package cn.j1angvei.cnbetareader.data.remote.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 2016/6/13.
 */
public final class CnbetaResult<T> {
    private List<T> list;

    public CnbetaResult() {
        list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
