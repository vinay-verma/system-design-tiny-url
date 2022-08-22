package com.zemoso.systemdesignbootcamp.tinyurlapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "tiny_urls")
public class TinyUrls implements Serializable {
    @Id
    private UUID id;

    @Column(name = "long_url", nullable = false, updatable = false)
    private String longUrl;

    @Column(name = "tiny_url", nullable = false, updatable = false)
    private String tinyUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "id", updatable = false)
    private Users users;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
}
