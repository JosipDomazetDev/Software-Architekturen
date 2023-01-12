package com.passwordsafe.subscriber;

import com.passwordsafe.logging.Logger;

public class WrongPasswordSubscriber implements IPasswordEventSubscriber{
    @Override
    public void update(String eventType, Logger logger) {
        logger.logInfo("ATTENTION: master password did not match! Failed to unlock.");
    }
}
