CREATE TABLE photos (
                        id SERIAL PRIMARY KEY,
                        url VARCHAR NOT NULL,
                        post_id INT NOT NULL REFERENCES auto_post(id) ON DELETE CASCADE
);