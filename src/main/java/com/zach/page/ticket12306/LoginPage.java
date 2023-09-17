package com.zach.page.ticket12306;

import com.zach.model.TicketContext;
import com.zach.page.AbstractPageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.security.auth.login.LoginContext;

/**
 * @author : zw35
 */
public class LoginPage extends TicketPageObject {
    @FindBy(xpath = "//*[@id=\"toolbar_Div\"]/div[2]/div[2]/ul/li[2]/a")
    private WebElement qcodeLogin;

    @FindBy(css = "//*[@id=\"J-qrImg\"]")
    private WebElement qcode;
    @FindBy(xpath = "//*[@id=\"J-index\"]/a")
    public WebElement home;


    public void qcodeLogin() {
        qcodeLogin.click();
        this.webDriverWaitUntil(ExpectedConditions.elementToBeClickable(home));
        TicketContext.login();

    }
}
