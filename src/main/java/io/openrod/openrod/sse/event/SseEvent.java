package io.openrod.openrod.sse.event;

public abstract class SseEvent<T extends Object> {

    public abstract String eventName();

    public abstract T data();

}
