package com.zach.model;

import java.util.concurrent.atomic.AtomicBoolean;

public class TicketContext {

    public static volatile AtomicBoolean isLogin = new AtomicBoolean(false);


    public static void login() {
        isLogin.set(true);
    }

    public static void logout() {
        isLogin.set(false);
    }

    public static boolean isLogin() {
        return isLogin.get();
    }

}
