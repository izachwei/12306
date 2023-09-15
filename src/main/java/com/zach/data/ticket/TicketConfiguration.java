package com.zach.data.ticket;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

@LoadPolicy(LoadType.MERGE)
@Config.Sources({
    "classpath:data/12306/12306.properties",
    "classpath:data/12306/startup.properties"})
public interface TicketConfiguration extends Config {
    @Key("stationLink")
    String stationLink();

    @Key("stationStr")
    String stationStr();

    @Key("seatType")
    String seatType();

    @Key("from")
    String from();

    @Key("to")
    String to();

    @Key("date")
    String date();

    @Key("trainNumber")
    String trainNumber();

    @Key("lever")
    String[] lever();

    @Key("passenger")
    String[] passenger();
}
