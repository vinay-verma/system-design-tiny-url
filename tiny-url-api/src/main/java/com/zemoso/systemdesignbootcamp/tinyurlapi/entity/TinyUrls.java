package com.zemoso.systemdesignbootcamp.tinyurlapi.entity;

import com.zemoso.systemdesignbootcamp.tinyurlapi.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(Constants.COLLECTION_TINY_URLS)
public class TinyUrls implements Serializable {
    @Id
    private String id;

    @BsonProperty("long_url")
    private String longUrl;

    @BsonProperty("tiny_url")
    private String tinyUrl;

    private String owner;

    @BsonProperty("created_at")
    private Date createdAt;
}
