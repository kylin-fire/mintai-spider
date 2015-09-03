package com.mintai.spider;

import com.google.common.collect.Multimap;
import org.openqa.selenium.WebDriver;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class MinTaiSpider {
    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("userName/password is required");
        }

        String userName = "";
        String password = "";

        RTSourceSpider spider = new RTSourceSpider();

        // 调度
        WebDriver driver = spider.craw(userName, password);

        OutputHelper.outputSource("RealTime", driver.getCurrentUrl(), driver.getPageSource());

        Multimap<String, RealTimeSourceDO> platform = ExtractHelper.extractPlatform(driver);
        OutputHelper.outputExtract("RealTime", platform);

        Multimap<String, RealTimeSourceDO> region = ExtractHelper.extractRegion(driver);
        OutputHelper.outputExtract("RealTime", region);

        // 退出
        spider.destroy();
    }
}
