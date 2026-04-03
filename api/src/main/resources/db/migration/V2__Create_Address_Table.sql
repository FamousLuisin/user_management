CREATE TABLE tb_address (
    id SERIAL PRIMARY KEY,
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    complemento VARCHAR(100),

    client_id INTEGER UNIQUE,
    CONSTRAINT fk_client_address FOREIGN KEY (client_id) REFERENCES tb_clients(id)
);