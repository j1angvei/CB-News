package cn.j1angvei.cnbetareader.bean;

/**
 * Created by Wayne on 2016/8/28.
 */

public class MyTopic extends Topic {
    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MyTopic myTopic = (MyTopic) o;

        return order == myTopic.order;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + order;
        return result;
    }
}
