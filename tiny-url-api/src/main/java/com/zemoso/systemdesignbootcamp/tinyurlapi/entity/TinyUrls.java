package com.zemoso.systemdesignbootcamp.tinyurlapi.entity;

import com.zemoso.systemdesignbootcamp.tinyurlapi.utils.Constants;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Document(Constants.COLLECTION_TINY_URLS)
public class TinyUrls implements Serializable {
    @Id
    private UUID id;

    @BsonProperty("long_url")
    private String longUrl;

    @BsonProperty("tiny_url")
    private String tinyUrl;

    private UUID owner;

    @BsonProperty("created_at")
    private Date createdAt = new Date();
}
