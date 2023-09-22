package com.zach.page.ticket12306;

import com.zach.exceptions.OrderException;
import com.zach.model.TicketInfo;
import com.zach.page.AbstractPageObject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class OrderPage extends TicketPageObject {

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
        if (isExist(By.xpath("//*[@id=\"defaultwarningAlert_id\"]"))) {
            String msg = getDriver().findElement(By.xpath("//*[@id=\"defaultwarningAlert_id\"]")).getText();
            log.error("订单创建失败，失败原因：{}", msg);
            if (msg.contains("乘车")) {
                log.error("乘客：{} 在当前登录12306账号中不存在", Arrays.toString(ticketInfo.getPassenger()));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                throw new OrderException("订单创建失败，失败原因：" + msg);
            }
        }
        webDriverWaitUntil(ExpectedConditions.elementToBeClickable(confirm));
        confirm.click();
    }


}
