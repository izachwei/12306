package com.zach.data.ticket;

import com.zach.model.TicketInfo;
import com.zach.util.TrainUtil;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigCache;

/**
 * @author : zw35
 */
@Slf4j
public class Ticket12306DataFactory {


    public static TicketInfo createTicketData() {
        TicketConfiguration ticketData = ConfigCache.getOrCreate(TicketConfiguration.class);

        return TicketInfo.builder().from(TrainUtil.codeByCity(ticketData.from()))
                .to(TrainUtil.codeByCity(ticketData.to()))
                .date(ticketData.date())
                .trainNumber(ticketData.trainNumber())
                .level(ticketData.lever())
                .passenger(ticketData.passenger())
                .build();
    }
}
