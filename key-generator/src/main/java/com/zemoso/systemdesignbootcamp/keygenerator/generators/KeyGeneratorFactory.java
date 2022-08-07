package com.zemoso.systemdesignbootcamp.keygenerator.generators;

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
                        .withNodeId(nodeId)
                        .build();
                return new TwitterSnowflakeKeyGenerator(config);
        }
        throw new IllegalArgumentException(String.format("No key generator found with name %s", generator));
    }
}
