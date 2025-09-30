INSERT INTO collection_point (name, formatted_address, latitude, longitude) VALUES ('EcoPonto Central', 'Rua das Flores, 123 - Centro', -23.550520, -46.633308);
INSERT INTO collection_point (name, formatted_address, latitude, longitude) VALUES ('Recicla Bairro Verde', 'Av. Sustentável, 456 - Bairro Verde', -23.558720, -46.625980);
INSERT INTO collection_point (name, formatted_address, latitude, longitude) VALUES ('Ponto de Coleta Jardim Azul', 'Rua Ecológica, 789 - Jardim Azul', -23.562100, -46.642500);
INSERT INTO collection_point (name, formatted_address, latitude, longitude) VALUES ('EcoRecicla Zona Norte', 'Av. do Progresso, 321 - Zona Norte', -23.494900, -46.625400);

INSERT INTO residue (name) VALUES ('Papel');
INSERT INTO residue (name) VALUES ('Papelão');
INSERT INTO residue (name) VALUES ('Embalagens longa-vida');
INSERT INTO residue (name) VALUES ('Plástico PET');
INSERT INTO residue (name) VALUES ('Plástico rígido');
INSERT INTO residue (name) VALUES ('Plástico filme e sacolas');
INSERT INTO residue (name) VALUES ('Metal - Alumínio');
INSERT INTO residue (name) VALUES ('Metal - Aço/Ferro');
INSERT INTO residue (name) VALUES ('Vidro transparente');
INSERT INTO residue (name) VALUES ('Vidro colorido');
INSERT INTO residue (name) VALUES ('Eletrônicos');
INSERT INTO residue (name) VALUES ('Pilhas e baterias');
INSERT INTO residue (name) VALUES ('Óleo de cozinha usado');
INSERT INTO residue (name) VALUES ('Pneus');
INSERT INTO residue (name) VALUES ('Têxteis e tecidos');
INSERT INTO residue (name) VALUES ('Madeira reciclável');


INSERT INTO residues_collection_point (id_collection_point, id_residue) VALUES (1, 1), (1, 2), (1, 3);
INSERT INTO residues_collection_point (id_collection_point, id_residue) VALUES (2, 1), (2, 4);
INSERT INTO residues_collection_point (id_collection_point, id_residue) VALUES (3, 2), (3, 3), (3, 5);
INSERT INTO residues_collection_point (id_collection_point, id_residue) VALUES (4, 1), (4, 2), (4, 3), (4, 4);