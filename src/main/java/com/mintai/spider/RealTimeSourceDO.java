package com.mintai.spider;

import java.util.Date;
import java.util.List;

/**
 * �ļ�������ʵʱ������Դ
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class RealTimeSourceDO {
    private String source;
    private Date time;
    private List<RealTimeItem> sources;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<RealTimeItem> getSources() {
        return sources;
    }

    public void setSources(List<RealTimeItem> sources) {
        this.sources = sources;
    }
}
