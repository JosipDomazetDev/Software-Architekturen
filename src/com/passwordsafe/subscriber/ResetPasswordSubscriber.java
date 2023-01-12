package com.passwordsafe.subscriber;

import com.passwordsafe.logging.Logger;

public class ResetPasswordSubscriber implements IPasswordEventSubscriber{
    @Override
    public void update(String eventType, Logger logger) {
        logger.logInfo("ATTENTION: master password was reset!");
    }
}
