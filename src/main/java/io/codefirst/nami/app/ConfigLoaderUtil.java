package io.codefirst.nami.app;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigLoaderUtil {

    public static String getProperty(String key) {
        return SpringContext.getAppContext().getEnvironment().getProperty(key);
    }
}