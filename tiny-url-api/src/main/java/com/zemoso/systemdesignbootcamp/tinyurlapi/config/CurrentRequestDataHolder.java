package com.zemoso.systemdesignbootcamp.tinyurlapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedInheritableThreadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CurrentRequestDataHolder {
    public enum RequestKey {
        USER_EMAIL
    }

    private static final ThreadLocal<Map<RequestKey, String>> currentRequestData =
            new NamedInheritableThreadLocal<>("Current Request Data");

    /** Reset the request data for the current thread. */
    public static void clear() {
        currentRequestData.remove();
    }

    /**
     * Set data in request context
     *
     * @param key
     * @param value
     */
    public static void set(RequestKey key, String value) {
        if (Objects.isNull(currentRequestData.get())) {
            Map<RequestKey, String> reqData = new HashMap<>();
            currentRequestData.set(reqData);
        }
        currentRequestData.get().put(key, value);
    }

    /**
     * Get data from request context
     *
     * @param key
     * @return
     */
    public static String get(RequestKey key) {
        if (Objects.isNull(currentRequestData.get()) || !currentRequestData.get().containsKey(key)) {
            return null;
        } else {
            return currentRequestData.get().get(key);
        }
    }

    public static UserContext getUserContext() {
        return new UserContext(get(RequestKey.USER_EMAIL));
    }
}
