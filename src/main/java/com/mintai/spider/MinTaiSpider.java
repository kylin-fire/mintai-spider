package com.mintai.spider;

import com.google.common.collect.Multimap;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class MinTaiSpider {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            throw new IllegalArgumentException("userName/password is required");
        }
        final String userName = args[0];
        final String password = args[1];
        final String path=args[2];

        final RTSourceSpider spider = new RTSourceSpider();

        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    // 调度
                    WebDriver driver = spider.craw(userName, password);

                    OutputHelper.outputSource(path, "RealTime", driver.getCurrentUrl(), driver.getPageSource());

                    Multimap<String, RealTimeSourceDO> platform = ExtractHelper.extractPlatform(driver);
                    OutputHelper.outputExtract(path, "RealTime", platform);

                    Multimap<String, RealTimeSourceDO> region = ExtractHelper.extractRegion(driver);
                    OutputHelper.outputExtract(path, "RealTime", region);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // 每隔10分钟调度一次
        executor.scheduleAtFixedRate(runnable, 0, 300, TimeUnit.SECONDS);
    }
}
