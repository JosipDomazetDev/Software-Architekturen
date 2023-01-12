package com.passwordsafe.logging;

public class ConsoleLogger implements Logger {

    @Override
    public void logError(String msg) {
        System.out.println(msg);
    }

    @Override
    public void logInfo(String msg) {
        System.out.println(msg);
    }

    @Override
    public void logDebug(String msg) {
        System.out.println(msg);
    }
}
