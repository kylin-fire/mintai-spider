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

    private List<RealTimeItem> detail;

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public List<RealTimeItem> getDetail() {
        return detail;
    }

    public void setDetail(List<RealTimeItem> detail) {
        this.detail = detail;
    }
}
