package com.zach.page.ticket12306;

import com.zach.page.AbstractPageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author : zw35
 */
public class LoginPage extends AbstractPageObject {
    @FindBy(xpath = "//*[@id=\"toolbar_Div\"]/div[2]/div[2]/ul/li[2]/a")
    private WebElement qcodeLogin;

    @FindBy(css = "//*[@id=\"J-qrImg\"]")
    private WebElement qcode;

    public void qcodeLogin() {
        qcodeLogin.click();
    }
}
