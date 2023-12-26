CREATE TABLE transaction
(
    hash              VARCHAR(255) NOT NULL PRIMARY KEY,
    nonce             BIGINT,
    block_hash        VARCHAR(255),
    block_number      BIGINT,
    transaction_index BIGINT,
    from_address      VARCHAR(255),
    to_address        VARCHAR(255),
    value             VARCHAR(255),
    gas_price         BIGINT,
    gas               BIGINT,
    input             TEXT,
    creates           VARCHAR(255),
    public_key        VARCHAR(512),
    raw               TEXT,
    r                 VARCHAR(255),
    s                 VARCHAR(255),
    v                 VARCHAR(255)
)