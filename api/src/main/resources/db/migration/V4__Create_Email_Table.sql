CREATE TABLE tb_emails (
    id SERIAL PRIMARY KEY,
    email VARCHAR(75),

    client_id INTEGER NOT NULL,
    CONSTRAINT fk_client_email FOREIGN KEY (client_id) REFERENCES tb_clients(id)
);