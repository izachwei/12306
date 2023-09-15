/*
 * MIT License
 *
 * Copyright (c) 2018 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zach;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.zach.driver.DriverManager;
import com.zach.driver.TargetFactory;
import com.zach.report.AllureManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static com.zach.config.ConfigurationManager.configuration;

@Listeners({TestListener.class})
public abstract class BaseWeb {

    private WebDriverWait webDriverWait;

    @BeforeSuite
    public void beforeSuite() {
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "10809");
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "10809");
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\86191\\.cache\\selenium\\chromedriver\\win32\\112.0.5615.49\\chromedriver.exe");
        AllureManager.setAllureEnvironmentInformation();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void preCondition(@Optional("chrome") String browser) {
        WebDriver driver = new TargetFactory().createInstance(browser);
        DriverManager.setDriver(driver);
        DriverManager.getDriver().get(configuration().url());
        waitPageLoaded();
    }

    private WebDriverWait getWebDriveWait() {
        if (webDriverWait == null) {
            webDriverWait = new WebDriverWait(DriverManager.getDriver()
                    , Duration.of(200, ChronoUnit.SECONDS));
        }
        return webDriverWait;
    }

    @AfterMethod(alwaysRun = true)
    public void postCondition() {
        DriverManager.quit();
    }

    public <T> void webDriverWaitUntil(ExpectedCondition<T> expectedCondition) {
        WebDriver driver = DriverManager.getDriver();
        try {
            getWebDriveWait().until(expectedCondition);
        } catch (TimeoutException var4) {
            ((JavascriptExecutor) driver).executeScript("window.stop();", new Object[0]);
        }
    }

    public void waitPageLoaded() {
        ExpectedCondition<Boolean> expectation = (e) -> {
            assert e != null;
            Object o = ((JavascriptExecutor) e).executeScript("return document.readyState", new Object[0]);
            return o == null ? false : "complete".equals(o.toString());
        };
        webDriverWaitUntil(expectation);
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
