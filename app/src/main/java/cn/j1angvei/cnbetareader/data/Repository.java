package cn.j1angvei.cnbetareader.data;

/**
 * Created by Wayne on 2016/7/5.
 */
public interface Repository extends DataSource {
    void initDataContainer();

    void toMemory(String source, Object item);
}
