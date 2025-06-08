package io.openrod.openrod.sse.impl;

import io.openrod.openrod.sse.SseService;
import io.openrod.openrod.sse.event.SseEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseServiceImpl implements SseService {

    private final Set<SseEmitter> emitters = ConcurrentHashMap.newKeySet();

    public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter(0L); // No timeout

        emitters.add(emitter);

        // Remove emitter when completed or timeout
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((ex) -> emitters.remove(emitter));

        return emitter;
    }

    public void sendToAll(final SseEvent<?> event) {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name(event.eventName())
                        .data(event.data()));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });

        // Clean up dead emitters
        deadEmitters.forEach(emitters::remove);
    }

    @EventListener
    public void onSseEvent(final SseEvent<?> event) {
        this.sendToAll(event);
    }
}