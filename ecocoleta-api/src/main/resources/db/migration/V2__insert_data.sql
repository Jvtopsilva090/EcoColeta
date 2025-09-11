INSERT INTO residue (name) VALUES ('Plástico');
INSERT INTO residue (name) VALUES ('Vidro');
INSERT INTO residue (name) VALUES ('Metal');
INSERT INTO residue (name) VALUES ('Papel');
INSERT INTO residue (name) VALUES ('Eletrônicos');

INSERT INTO collection_point (name, formatted_address, latitude, longitude) VALUES ('EcoPonto Central', 'Rua das Flores, 123 - Centro', -23.550520, -46.633308);
INSERT INTO collection_point (name, formatted_address, latitude, longitude) VALUES ('Recicla Bairro Verde', 'Av. Sustentável, 456 - Bairro Verde', -23.558720, -46.625980);
INSERT INTO collection_point (name, formatted_address, latitude, longitude) VALUES ('Ponto de Coleta Jardim Azul', 'Rua Ecológica, 789 - Jardim Azul', -23.562100, -46.642500);
INSERT INTO collection_point (name, formatted_address, latitude, longitude) VALUES ('EcoRecicla Zona Norte', 'Av. do Progresso, 321 - Zona Norte', -23.494900, -46.625400);

INSERT INTO collection_point_residues (collection_point_id, residue_id) VALUES (1, 1), (1, 2), (1, 3);
INSERT INTO collection_point_residues (collection_point_id, residue_id) VALUES (2, 1), (2, 4);
INSERT INTO collection_point_residues (collection_point_id, residue_id) VALUES (3, 2), (3, 3), (3, 5);
INSERT INTO collection_point_residues (collection_point_id, residue_id) VALUES (4, 1), (4, 2), (4, 3), (4, 4);