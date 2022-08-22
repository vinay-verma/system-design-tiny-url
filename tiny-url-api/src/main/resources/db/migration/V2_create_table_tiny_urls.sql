CREATE TABLE IF NOT EXISTS tiny_urls (
    id VARCHAR(36) NOT NULL,
    url VARCHAR(2000) NOT NULL,
    hash VARCHAR(11) NOT NULL UNIQUE,
    owner VARCHAR(36),

    PRIMARY KEY (id),
    CONSTRAINT fk_tiny_urls_users FOREIGN KEY (owner) REFERENCES users(id),
    UNIQUE (id),
    UNIQUE (hash),
    UNIQUE (url, owner)
);
