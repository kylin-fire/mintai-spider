package com.mintai.spider;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class RTSourceSpider {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    public RTSourceSpider() {
        driver = new FirefoxDriver();
        baseUrl = "http://live.sycm.taobao.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public WebDriver craw(String userName, String password) {
        // 实时直播
        driver.get(baseUrl + "index.htm");

        if (driver.getCurrentUrl().contains("login.htm")) {
            // 登录
            while (true) {
                String text = driver.findElement(By.id("mod-banner")).getText();
                if (text != null && !text.isEmpty()) {
                    break;
                }
            }

            driver.switchTo().frame(0);
            driver.findElement(By.id("TPL_username_1")).sendKeys(userName);
            driver.findElement(By.id("TPL_password_1")).sendKeys(password);
            driver.findElement(By.id("J_SubmitStatic")).click();

            while (driver.getCurrentUrl().contains("login.taobao.com")) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }

            driver.switchTo().defaultContent();
        }

        // 实时来源
        driver.get(baseUrl + "source.htm?_res_id_=7");

        return driver;
    }

    public void destroy() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
