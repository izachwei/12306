package com.zach.page.ticket12306;

import java.util.List;
import java.util.Map;

import com.zach.model.TicketInfo;
import com.zach.page.AbstractPageObject;
import com.zach.util.TrainUtil;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

/**
 * @author : zw35
 */
public class TicketListPage extends AbstractPageObject {

    @FindAll({@FindBy(xpath = "//*[@id=\"queryLeftTable\"]/tr")})
    public List<WebElement> ticketList;


    public void order(TicketInfo ticketData) {
        String seatType = "";
        for (WebElement webElement : ticketList) {
            /*
                C762
                复
                成都东
                广安南
                06:43
                09:12
                02:29
                当日到达
                -- 有 有 -- -- -- -- -- -- 有 -- 预订
             */

            String text = webElement.getText();
            String[] split = text.split("\n");
            String trainNumber = split[0];
            String[] level = ticketData.getLevel();
            if (StringUtils.equals(ticketData.getTrainNumber(), trainNumber)) {
                String seat = split[split.length - 1];
                Map<String, Boolean> stringBooleanMap = TrainUtil.parseSeat(seat);
                for (int i = 0; i < ticketData.getLevel().length; i++) {
                    if (stringBooleanMap.getOrDefault(level[i], false)) {
                        seatType = level[i];
                        WebElement element = webElement.findElement(By.cssSelector("td.no-br > a"));
                        element.click();
                        break;
                    }
                }
            }
        }
        if (StringUtils.isEmpty(seatType)) {
            throw new RuntimeException("无余票");
        }
    }
}
