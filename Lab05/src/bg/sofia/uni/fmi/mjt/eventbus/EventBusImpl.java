package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EventBusImpl implements EventBus {
    private final HashMap<Class<? extends Event<?>>, ArrayList<Subscriber<?>>> events;

    public EventBusImpl() {
        this.events = new HashMap<>();
    };

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        try {
            if(eventType == null) throw new IllegalArgumentException("Event cannot be null!");
            if(subscriber == null) throw new IllegalArgumentException("Subscriber cannot be null!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        ArrayList<Subscriber<?>> subscribers = events.get(eventType);
        if (subscribers == null) {
            subscribers = new ArrayList<>();
            events.put(eventType, subscribers);
        }
        subscribers.add(subscriber);
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber) throws MissingSubscriptionException {
        try {
            if(eventType == null) throw new IllegalArgumentException("Event cannot be null!");
            if(subscriber == null) throw new IllegalArgumentException("Subscriber cannot be null!");
            if(!events.get(eventType).contains(subscriber)) throw new MissingSubscriptionException("The user has not subscribed to this event!");
        } catch (IllegalArgumentException | MissingSubscriptionException e) {
            throw e;
        }
        events.get(eventType).remove(subscriber);
    }

    @Override
    public <T extends Event<?>> void publish(T event) {
        try {
            if(event == null) throw new IllegalArgumentException("Event cannot be null!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        for (Subscriber subscriber : events.get(event.getClass())) {
            subscriber.onEvent(event);
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        return List.of();
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        try {
            if(eventType == null) throw new IllegalArgumentException("Event cannot be null!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        return events.get(eventType);
    }
}
