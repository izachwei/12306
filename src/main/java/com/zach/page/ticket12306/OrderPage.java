package com.zach.page.ticket12306;

import com.zach.model.TicketInfo;
import com.zach.page.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

public class OrderPage extends AbstractPageObject {

    @FindBy(xpath = "//*[@id=\"normal_passenger_id\"]/li")
    private List<WebElement> passengerElement;

    @FindBy(xpath = "//*[@id=\"submitOrder_id\"]")
    private WebElement submit;

    @FindBy(xpath = "//*[@id=\"qr_submit_id\"]")
    private WebElement confirm;


    public void createOrder(TicketInfo ticketInfo) {
        List<String> passenger = Arrays.stream(ticketInfo.getPassenger()).toList();
        for (WebElement webElement : passengerElement) {
            String name = webElement.getText();
            if (passenger.contains(name)) {
                webElement.findElement(By.cssSelector("input")).click();
            }
        }
        submit.click();
        webDriverWaitUntil(ExpectedConditions.elementToBeClickable(confirm));
        confirm.click();
    }

}
