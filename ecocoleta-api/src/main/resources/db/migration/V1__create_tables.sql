CREATE TABLE collection_point (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    formatted_address VARCHAR(255) NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    CONSTRAINT collection_point_pk PRIMARY KEY (id),
    CONSTRAINT latitude_longitude_uk UNIQUE (latitude, longitude)
);