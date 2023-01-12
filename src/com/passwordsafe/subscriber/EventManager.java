package com.passwordsafe.subscriber;

import com.passwordsafe.logging.Logger;

import java.util.*;

public class EventManager {
    Map<String, List<IPasswordEventSubscriber>> listeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, IPasswordEventSubscriber listener) {
        List<IPasswordEventSubscriber> passwordEventSubscribers = listeners.get(eventType);
        passwordEventSubscribers.add(listener);
    }

    public void unsubscribe(String eventType, IPasswordEventSubscriber listener) {
        List<IPasswordEventSubscriber> passwordEventSubscribers = listeners.get(eventType);
        passwordEventSubscribers.remove(listener);
    }

    public void notify(String eventType, Logger logger) {
        List<IPasswordEventSubscriber> passwordEventSubscribers = listeners.get(eventType);
        for (IPasswordEventSubscriber passwordEventSubscriber : passwordEventSubscribers) {
            passwordEventSubscriber.update(eventType, logger);
        }
    }
}
