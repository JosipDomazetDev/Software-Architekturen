package com.passwordsafe.logging;

public class ConsoleLoggerFactory implements LoggerFactory{
    @Override
    public Logger createLogger() {
        return new ConsoleLogger();
    }
}
