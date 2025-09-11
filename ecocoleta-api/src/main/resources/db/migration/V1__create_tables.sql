CREATE TABLE collection_point (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    formatted_address VARCHAR(255) NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    CONSTRAINT collection_point_pk PRIMARY KEY (id),
    CONSTRAINT latitude_longitude_uk UNIQUE (latitude, longitude)
);

CREATE TABLE residue (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT residue_pk PRIMARY KEY (id)
);

CREATE TABLE collection_point_residues (
    collection_point_id INTEGER NOT NULL,
    residue_id INTEGER NOT NULL,
    CONSTRAINT collection_point_residues_pk PRIMARY KEY (collection_point_id, residue_id),
    CONSTRAINT collection_point_fk FOREIGN KEY (collection_point_id) REFERENCES collection_point (id),
    CONSTRAINT residue_fk FOREIGN KEY (residue_id) REFERENCES residue (id)
);