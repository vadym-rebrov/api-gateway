package dev.profitsoft.internship.rebrov.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("=== INCOMING REQUEST ===");
        logger.info("Method: {}", exchange.getRequest().getMethod());
        logger.info("URI: {}", exchange.getRequest().getURI());
        logger.info("Path: {}", exchange.getRequest().getPath());

        var headers = exchange.getRequest().getHeaders();
        logger.info("Origin: {}", headers.getOrigin());
        logger.info("Cookie: {}", headers.get("Cookie"));
        logger.info("Authorization: {}", headers.get("Authorization"));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("=== OUTGOING RESPONSE ===");
            logger.info("Status Code: {}", exchange.getResponse().getStatusCode());
            logger.info("=========================");
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}