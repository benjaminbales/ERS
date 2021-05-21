package com.ex;

import java.util.Map;

public abstract class AbstractApplication {
    protected Map<String, Object> context;
    public abstract void run();
    public Map<String, Object> getContext() {
        return this.context;
    }
}
