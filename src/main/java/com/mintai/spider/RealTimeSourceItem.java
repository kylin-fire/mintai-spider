package com.mintai.spider;

import java.util.List;

/**
 * ÎÄ¼şÃèÊö£º
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class RealTimeSourceItem extends RealTimeItem {
    private double percent;


    private List<RealTimeSourceItem> detail;

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public List<RealTimeSourceItem> getDetail() {
        return detail;
    }

    public void setDetail(List<RealTimeSourceItem> detail) {
        this.detail = detail;
    }
}
