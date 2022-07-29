package com.verma.keygenerator.generators;

import lombok.Builder;
import lombok.NonNull;

@Builder(setterPrefix = "with",buildMethodName = "load")
public class KeyGeneratorFactory {
    @NonNull
    private Integer nodeId;

    @NonNull
    private String generator;

    public KeyGenerator generator() {
        switch (generator) {
            case "TwitterSnowflakeKeyGenerator":
                TwitterSnowflakeKeyGenerator.TwitterSnowflakeKeyGeneratorConfig config =
                        TwitterSnowflakeKeyGenerator.TwitterSnowflakeKeyGeneratorConfig
                        .builder()
                        .nodeId(nodeId)
                        .build();
                return new TwitterSnowflakeKeyGenerator(config);
        }
        throw new IllegalArgumentException(String.format("No key generator registered with name %s", generator));
    }
}
