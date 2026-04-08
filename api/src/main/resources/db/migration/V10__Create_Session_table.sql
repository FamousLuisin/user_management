CREATE TABLE tb_refresh_tokens (
    id SERIAL PRIMARY KEY,
    token TEXT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    user_id INTEGER NOT NULL,

    CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_id) REFERENCES tb_users(id) ON DELETE CASCADE
);