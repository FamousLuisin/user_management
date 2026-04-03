CREATE TABLE tb_phones (
    id SERIAL PRIMARY KEY,
    type VARCHAR(15),
    number VARCHAR(12),

    client_id INTEGER NOT NULL,
    CONSTRAINT fk_client_phone FOREIGN KEY (client_id) REFERENCES tb_clients(id)
);