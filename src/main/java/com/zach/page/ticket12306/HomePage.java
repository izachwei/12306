package com.zach.page.ticket12306;

import com.zach.driver.DriverManager;
import com.zach.exceptions.PageErrorException;
import com.zach.model.TicketContext;
import com.zach.model.TicketInfo;
import com.zach.page.AbstractPageObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.security.auth.login.LoginContext;
import java.util.Set;

/**
 * @author : zw35
 */
public class HomePage extends TicketPageObject {

    @FindBy(id = "J-btn-login")
    public WebElement loginElement;
    @FindBy(xpath = "//*[@id=\"J-index\"]/a")
    public WebElement home;

    @FindBy(xpath = "//*[@id=\"fromStation\"]")
    public WebElement fromStation;

    @FindBy(xpath = "//*[@id=\"toStation\"]")
    public WebElement toStation;

    @FindBy(xpath = "//*[@id=\"train_date\"]")
    public WebElement trainDate;

    @FindBy(xpath = "//*[@id=\"toolbar_Div\"]/div[3]/div[2]/div/div[1]/div/div[2]/div[1]/div[1]/div[4]")
    public WebElement search;

    public void login() {
        loginElement.click();
    }

    public void go() {
        this.webDriverWaitUntil(ExpectedConditions.elementToBeClickable(home));
        home.click();
        this.check();
    }

    public void search(TicketInfo ticket) {
        String currentWindow = getDriver().getWindowHandle();

        this.webDriverWaitUntil(ExpectedConditions.elementToBeClickable(search));
        JavascriptExecutor driver = (JavascriptExecutor) getDriver();
        driver.executeScript("arguments[0].value=arguments[1]", fromStation, ticket.getFrom());
        driver.executeScript("arguments[0].value=arguments[1]", toStation, ticket.getTo());
        driver.executeScript("arguments[0].value=arguments[1]", trainDate, ticket.getDate());
        search.click();
        Set<String> windowHandles = DriverManager.getDriver().getWindowHandles();
        String nextWindow = "";
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(currentWindow)) {
                nextWindow = windowHandle;
            }
        }
        getDriver().switchTo().window(nextWindow);

        this.check();
    }

}
