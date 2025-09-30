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
    name VARCHAR(255),
    CONSTRAINT pk_residue PRIMARY KEY (id)
);

CREATE TABLE residues_collection_point (
    id SERIAL NOT NULL,
    id_collection_point INTEGER NOT NULL,
    id_residue INTEGER NOT NULL,
    CONSTRAINT pk_residues_collection_point PRIMARY KEY (id),
    CONSTRAINT fk_collection_point FOREIGN KEY (id_collection_point) REFERENCES collection_point (id),
    CONSTRAINT fk_residues FOREIGN KEY (id_residue) REFERENCES residue (id)
);