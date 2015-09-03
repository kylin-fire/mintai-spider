package com.mintai.spider;

import com.google.common.collect.Multimap;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class RTSourceSpiderTest {
    @Test
    public void testRTSource() throws Exception {
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
