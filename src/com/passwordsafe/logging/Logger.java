package com.passwordsafe.logging;

public interface Logger {
    void logError(String msg);
    void logInfo(String msg);
    void logDebug(String msg);
}
