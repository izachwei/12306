package com.zach.test;

import com.zach.BaseWeb;
import com.zach.data.ticket.Ticket12306DataFactory;
import com.zach.model.TicketContext;
import com.zach.model.TicketInfo;
import com.zach.page.ticket12306.HomePage;
import com.zach.page.ticket12306.LoginPage;
import com.zach.page.ticket12306.OrderPage;
import com.zach.page.ticket12306.TicketListPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.sql.Driver;

/**
 * @author : zw35
 */
@Slf4j
public class Ticket12306Test extends BaseWeb {


    @Test(description = "12306")
    public void ticket() throws InterruptedException {
        var ticketData = Ticket12306DataFactory.createTicketData();


        boolean res = false;
        while (!res) {
            res = buyTicket(ticketData);
        }
        log.info("抢票成功,请支付");
        Thread.sleep(10000);
    }

    private boolean buyTicket(TicketInfo ticketData) {
        try {
            initPage();
            var homePage = new HomePage();
            if (!TicketContext.isLogin()) {
                homePage.login();
                waitPageLoaded();
                LoginPage loginPage = new LoginPage();
                loginPage.qcodeLogin();
            }
            homePage.go();
            homePage.search(ticketData);
            waitPageLoaded();
            TicketListPage ticketListPage = new TicketListPage();
            ticketListPage.predetermine(ticketData);
            waitPageLoaded();
            OrderPage orderPage = new OrderPage();
            orderPage.createOrder(ticketData);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
