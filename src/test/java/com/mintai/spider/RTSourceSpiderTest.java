package com.mintai.spider;

import com.google.common.collect.Multimap;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class RTSourceSpiderTest {
    @Test
    public void testRTSource() throws Exception {
        String userName = "";
        String password = "";
        String path = "d:/mintai/";

        RTSourceSpider spider = new RTSourceSpider();

        // 调度
        WebDriver driver = spider.craw(userName, password);

        OutputHelper.outputSource(path, "RealTime", driver.getCurrentUrl(), driver.getPageSource());

        Multimap<String, RealTimeSourceDO> platform = ExtractHelper.extractPlatform(driver);
        OutputHelper.outputExtract(path, "RealTime", platform);

        Multimap<String, RealTimeSourceDO> region = ExtractHelper.extractRegion(driver);
        OutputHelper.outputExtract(path, "RealTime", region);

        // 退出
        spider.destroy();
    }
}
