package com.zemoso.systemdesignbootcamp.keygenerator.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@RedisHash("Key")
public class Key implements Serializable {
    @Id
    private String id;

    private boolean used;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public Key(String id) {
        this.id = id;
    }
}
