package io.openrod.openrod.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {

    SseEmitter createEmitter();

}
