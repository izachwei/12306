package com.zach.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author : zw35
 */
@Data
@Builder
public class TicketInfo {

    private String from;

    private String to;

    private String date;

    private String trainNumber;

    private String[] level;

    private String[] passenger;

}
