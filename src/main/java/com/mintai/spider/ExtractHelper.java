package com.mintai.spider;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.List;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class ExtractHelper {
    public static Multimap<String, RealTimeSourceDO> extractPlatform(WebDriver driver) {
        WebElement root = driver.findElement(By.id("page-live-source"));

        Multimap<String, RealTimeSourceDO> multimap = HashMultimap.create();

        // 抽取数据
        List<WebElement> platform = root.findElements(By.cssSelector("div.component-source-device"));

        for (WebElement each : platform) {
            RealTimeSourceDO sourceDO = getRealTimeSource(each);
            multimap.put("platform", sourceDO);
        }

        return multimap;
    }

    public static Multimap<String, RealTimeSourceDO> extractRegion(WebDriver driver) {
        WebElement root = driver.findElement(By.id("page-live-source"));

        Multimap<String, RealTimeSourceDO> multimap = HashMultimap.create();

        // 抽取数据
        WebElement region = root.findElement(By.cssSelector("div.component-source-region"));
        RealTimeSourceDO sourceDO = getRealTimeRegion(region);
        multimap.put("region", sourceDO);

        return multimap;
    }

    private static RealTimeSourceDO getRealTimeSource(WebElement element) {
        RealTimeSourceDO sourceDO = new RealTimeSourceDO();
        String source = element.findElement(By.cssSelector("h4.navbar-header > span")).getText();
        sourceDO.setSource(source);

        String time = element.findElement(By.cssSelector("div.mod-box-update-time > span > em")).getText();
        Date date = CommonHelper.toDate(time, "yyyy-MM-dd HH:mm:ss");
        sourceDO.setTime(date);

        List<WebElement> rows = element.findElements(By.cssSelector("div.table-body > div.row"));

        List<RealTimeItem> sourceList = getRealTimeItems(rows);
        sourceDO.setSources(sourceList);

        return sourceDO;
    }

    private static List<RealTimeItem> getRealTimeItems(List<WebElement> rows) {
        List<RealTimeItem> sourceList = null;
        if (rows != null && !rows.isEmpty()) {
            sourceList = Lists.newArrayList();

            for (WebElement row : rows) {
                RealTimeSourceItem item = getRealTimeSourceItem(row);
                sourceList.add(item);
            }
        }
        return sourceList;
    }

    private static RealTimeSourceItem getRealTimeSourceItem(WebElement row) {
        RealTimeSourceItem item = new RealTimeSourceItem();

        String from = row.findElement(By.cssSelector("span.source > span")).getText();
        String percent = row.findElement(By.cssSelector("span.percent")).getText();
        if (CommonHelper.isNotBlank(percent) && percent.contains("%")) {
            percent = percent.replace("%", "");
        }
        String pv = row.findElement(By.cssSelector("span.pv")).getText();

        item.setFrom(from);
        item.setPercent(Double.valueOf(percent));
        item.setVisitor(Integer.valueOf(pv));

        WebElement child = row.findElement(By.cssSelector("ul.row-children"));

        if(!child.isDisplayed()){
            row.findElement(By.cssSelector("span.source")).click();
        }

        List<WebElement> children = child.findElements(By.cssSelector("li"));

        if (children != null && !children.isEmpty()) {
            List<RealTimeItem> details = getRealTimeChildItems(children);

            item.setDetail(details);
        }
        return item;
    }

    private static List<RealTimeItem> getRealTimeChildItems(List<WebElement> rows) {
        List<RealTimeItem> sourceList = null;
        if (rows != null && !rows.isEmpty()) {
            sourceList = Lists.newArrayList();

            for (WebElement row : rows) {
                RealTimeSourceItem item = getRealTimeChildSourceItem(row);
                if (item != null) {
                    sourceList.add(item);
                }
            }
        }
        return sourceList;
    }

    private static RealTimeSourceItem getRealTimeChildSourceItem(WebElement row) {
        String from = row.findElement(By.cssSelector("span.source")).getText();
        if (CommonHelper.isNotBlank(from)) {
            RealTimeSourceItem item = new RealTimeSourceItem();

            String percent = row.findElement(By.cssSelector("span.percent")).getText();
            if (CommonHelper.isNotBlank(percent) && percent.contains("%")) {
                percent = percent.replace("%", "");
            }
            String pv = row.findElement(By.cssSelector("span.pv")).getText();

            item.setFrom(from);
            if (CommonHelper.isNotBlank(percent)) {
                item.setPercent(Double.valueOf(percent));
            }
            if (CommonHelper.isNotBlank(pv)) {
                item.setVisitor(Integer.valueOf(pv));
            }
            return item;
        }
        return null;
    }

    private static RealTimeSourceDO getRealTimeRegion(WebElement element) {
        RealTimeSourceDO sourceDO = new RealTimeSourceDO();
        String source = element.findElement(By.cssSelector("h4.navbar-header > span")).getText();
        sourceDO.setSource(source);

        String time = element.findElement(By.cssSelector("div.mod-box-update-time > span > em")).getText();
        Date date = CommonHelper.toDate(time, "yyyy-MM-dd HH:mm:ss");
        sourceDO.setTime(date);

        List<WebElement> rows = element.findElements(By.cssSelector("dl.map-rank-list > dd"));

        if (rows != null && !rows.isEmpty()) {
            List<RealTimeItem> sourceList = Lists.newArrayList();
            sourceDO.setSources(sourceList);
            for (WebElement row : rows) {
                RealTimeRegionItem item = new RealTimeRegionItem();
                String from = row.findElement(By.cssSelector("span.col-1")).getText();
                String pv = row.findElement(By.cssSelector("span.col-2")).getText();
                String paid = row.findElement(By.cssSelector("span.col-4")).getText();

                item.setFrom(from);
                item.setPaid(Integer.valueOf(paid));
                item.setVisitor(Integer.valueOf(pv));
                sourceList.add(item);
            }
        }
        return sourceDO;
    }

}
