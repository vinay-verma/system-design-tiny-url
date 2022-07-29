package com.verma.keygenerator.generators;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;

public class TwitterSnowflakeKeyGenerator implements KeyGenerator {
    private static final int UNUSED_BITS = 1; // Sign bit, Unused (always set to 0)
    private static final int EPOCH_BITS = 41;
    private static final int NODE_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final int MAX_SEQUENCE = (int) (Math.pow(2, SEQUENCE_BITS) - 1);
    private static final int MAX_NODE_ID = (int) (Math.pow(2, NODE_ID_BITS) - 1);

    private static final long CUSTOM_EPOCH = 1658534400000L; // 2022-07-23T00:00:00.000Z

    private volatile long lastTimeStamp = -1L;
    private volatile long sequence = 0L;

    private final TwitterSnowflakeKeyGeneratorConfig config;

    public TwitterSnowflakeKeyGenerator(TwitterSnowflakeKeyGeneratorConfig config) {
        if(config.getNodeId() < 0 || config.getNodeId() > MAX_NODE_ID) {
            throw new IllegalArgumentException(String.format("NodeId must be between %d and %d", 0, MAX_NODE_ID));
        }
        this.config = config;
    }

    @Override
    public long next() {
        long currentTimeStamp = currentTimeStamp();

        if (currentTimeStamp < lastTimeStamp) {
            throw new IllegalArgumentException("Invalid system clock !!");
        } else if (currentTimeStamp == lastTimeStamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // used all sequence upto allowed limit, wait for next time in millis
                currentTimeStamp = waitForNextCurrentTimestamp(currentTimeStamp);
            }
        } else {
            sequence = 0;
        }
        lastTimeStamp = currentTimeStamp;
        long id = currentTimeStamp << (NODE_ID_BITS + SEQUENCE_BITS);
        id |= (config.getNodeId() << SEQUENCE_BITS);
        id |= sequence;
        return id;
    }

    private long waitForNextCurrentTimestamp(long currentTimeStamp) {
        while(currentTimeStamp == lastTimeStamp) {
            currentTimeStamp = currentTimeStamp();
        }
        return currentTimeStamp;
    }

    private long currentTimeStamp() {
        return Instant.now().toEpochMilli() - CUSTOM_EPOCH;
    }

    @Builder
    @Getter
    public static class TwitterSnowflakeKeyGeneratorConfig implements KeyGeneratorConfig {
        @NonNull
        private Integer nodeId;
    }
}
