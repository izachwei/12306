package com.zach.page.ticket12306;

import com.zach.exceptions.PageErrorException;
import com.zach.page.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TicketPageObject extends AbstractPageObject {

    public boolean isError() {
        return isExist(By.xpath("//*[@id=\"error\"]"));
    }

    public void check(){
        if (isError()) {
            throw new PageErrorException("search page error.");
        }
    }
}
