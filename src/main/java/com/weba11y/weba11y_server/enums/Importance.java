package com.weba11y.weba11y_server.enums;

public enum Importance {
    CRITICAL(1),
    SERIOUS(2),
    MODERATE(3),
    MINOR(4);

    private final int order;

    Importance(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}