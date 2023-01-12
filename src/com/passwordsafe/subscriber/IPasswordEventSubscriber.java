package com.passwordsafe.subscriber;

import com.passwordsafe.logging.Logger;

public interface IPasswordEventSubscriber {
    void update(String eventType, Logger logger);
}