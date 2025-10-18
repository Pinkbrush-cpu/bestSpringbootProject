package com.czy.template.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final OnlineUserWebSocketHandler webSocketHandler;

    public WebSocketConfig(OnlineUserWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
        System.out.println("WebSocketConfig 初始化完成");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("注册 WebSocket 处理器");
        registry.addHandler(webSocketHandler, "/ws/online")
                .setAllowedOriginPatterns("*");
    }
}