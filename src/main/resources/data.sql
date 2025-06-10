-- ========================
-- Inserciones completas para dueldeck
-- ========================

-- Tipos de cartas
INSERT IGNORE INTO card_types (name) VALUES
('Monster'),
('Spell'),
('Trap');

-- Rarezas
INSERT IGNORE INTO card_rarities (name) VALUES
('Rare'),
('Super Rare'),
('Ultra Rare'),
('Secret Rare');

-- Tipados
INSERT IGNORE INTO card_typings (name) VALUES
('Normal'),
('Effect'),
('Fusion'),
('Synchro'),
('XYZ'),
('Link'),
('Ritual'),
('Pendulum');

-- Atributos
INSERT IGNORE INTO card_attributes (name) VALUES
('Light'),
('Dark'),
('Fire'),
('Water'),
('Earth'),
('Wind'),
('Divine');

-- Roles
INSERT IGNORE INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MANAGER'),
(3, 'ROLE_USER');

-- Usuarios
INSERT IGNORE INTO users (id, username, password, enabled, first_name, last_name, image) VALUES
(1, 'admin', '$2y$12$LU2iGOA36WD7SSaA8nYwLeTvEiaPmIufbQ5MiPTy0NfYRTc04ddIC', true, 'Admin', 'User', 'admin.jpg'),
(2, 'manager', '$2y$12$UiqBDeptXnYI01M4juVv.OnZm5xkJUR9m4gIrQKm0uuTFl1pN2Yn6', true, 'Manager', 'User', 'manager.jpg'),
(3, 'normal', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Regular', 'User', 'user.jpg');

-- Roles de usuario
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Expansiones
INSERT IGNORE INTO expansions (code, name, release_date, description) VALUES
('PHHY', 'Photon Hypernova', '2023-02-10', 'Una expansión con cartas de luz y oscuridad.'),
('DABL', 'Darkwing Blast', '2022-10-20', 'Fomenta nuevas estrategias con Synchros y Bestias Aladas.'),
('CYAC', 'Cyberstorm Access', '2023-04-14', 'Una expansión que introduce nuevas cartas de ciberredes y robots.'),
('DUEA', 'Duelist Alliance', '2014-08-15', 'Una expansión que ofrece nuevos arquetipos y estrategias.'),
('RDS', 'Rise of Destiny', '2003-11-04', 'Una expansión que introduce nuevas cartas de destino y magia.'),
('AST', 'Ancient Sanctuary', '2002-06-03', 'Una expansión centrada en los antiguos dioses y cartas de ritual.'),
('SPYR', 'Spy Mission', '2024-01-11', 'Una expansión temática sobre espionaje y tecnología avanzada.'),
('LODT', 'Labyrinth of Darkness', '2004-04-28', 'Una expansión llena de monstruos oscuros y trampas misteriosas.'),
('FLA', 'Flame of the Dragon', '2023-08-19', 'Una expansión centrada en dragones y fuego, ideal para jugadores agresivos.'),
('GEM', 'Gem Knights', '2015-09-18', 'Una expansión que destaca a los caballeros gemelos y estrategias de fusión.');

-- Cartas
INSERT IGNORE INTO cards (code, name, type_id, typing_id, attribute_id, level, attack, defense, rarity_id, image, description, expansion_id) VALUES
('LL001', 'Lady Labrynth', 1, 2, 2, 8, 2900, 1900, 3, 'lady_labrynth.jpg', 'Una dama enigmática con poderosos efectos de laberinto.', 1),
('GPD002', 'Galaxy Photon Dragon', 1, 2, 1, 8, 3000, 2500, 4, 'galaxy_photon_dragon.jpg', 'Dragón de luz que domina el campo con su brillo.', 1),
('BL003', 'Bystial Lubellion', 1, 2, 2, 6, 2500, 2000, 3, 'bystial_lubellion.jpg', 'Dragón oscuro con control de cementerio.', 2),
('KF004', 'Kashtira Fenrir', 1, 2, 2, 7, 2400, 1200, 4, 'kashtira_fenrir.jpg', 'Guerrero misterioso con poderes de exclusión.', 2),
('CYAC001', 'Cyberstorm Dragon', 1, 2, 1, 9, 3100, 2600, 4, 'cyberstorm_dragon.jpg', 'Un dragón cibernético con devastadores ataques.', 3),
('CYAC002', 'Cyber Blade', 1, 2, 1, 4, 1800, 1000, 3, '.jpg', 'Guerrera ágil con filo de energía.', 3),
('DUEA001', 'Duelist’s Magician', 2, 1, NULL, NULL, NULL, NULL, 1, 'duelist_magician.jpg', 'Magia ofensiva del duelist.', 4),
('DUEA002', 'Alliance’s Rage', 3, 1, NULL, NULL, NULL, NULL, 2, 'alliances_rage.jpg', 'Trampa para cambiar el curso del duelo.', 4),
('RDS001', 'Destiny Hero - Plasma', 1, 2, 2, 8, 1900, 600, 3, 'plasma.jpg', 'Un héroe oscuro con habilidades de negación.', 5),
('RDS002', 'Destiny Draw', 2, 1, NULL, NULL, NULL, NULL, 4, 'destiny_draw.jpg', 'Robo estratégico para Héroes del Destino.', 5),
('AST001', 'Ancient Sacred Dragon', 1, 4, 1, 7, 2700, 2100, 3, 'ancient_sacred_dragon.jpg', 'Dragón legendario de santuario antiguo.', 6),
('AST002', 'Sanctuary of Light', 2, 1, NULL, NULL, NULL, NULL, 1, 'sanctuary_light.jpg', 'Campo de energía divina.', 6),
('SPYR001', 'Spy Master', 1, 2, 2, 6, 2300, 1800, 4, 'spy_master.jpg', 'Espía maestro con tecnología furtiva.', 7),
('SPYR002', 'Tech Overload', 2, 1, NULL, NULL, NULL, NULL, 2, 'tech_overload.jpg', 'Sobrecarga tecnológica para oponentes.', 7),
('LODT001', 'Labyrinth Dragon', 1, 2, 2, 8, 2800, 2500, 3, 'labyrinth_dragon.jpg', 'Dragón oscuro del laberinto infinito.', 8),
('LODT002', 'Cursed Trap Hole', 3, 1, NULL, NULL, NULL, NULL, 4, 'cursed_trap_hole.jpg', 'Trampa mortal y maldita.', 8),
('FLA001', 'Dragon of Flames', 1, 2, 3, 7, 2600, 2100, 3, 'dragon_flames.jpg', 'Dragón abrasador con ímpetu.', 9),
('FLA002', 'Inferno Blaze', 3, 1, NULL, NULL, NULL, NULL, 2, 'inferno_blaze.jpg', 'Trampa ardiente.', 9),
('GEM001', 'Gem Knight Ruby', 1, 3, 5, 6, 2500, 1300, 3, 'gem_knight_ruby.jpg', 'Caballero brillante con poder de fusión.', 10),
('GEM002', 'Gem Fusion', 2, 1, NULL, NULL, NULL, NULL, 1, 'gem_fusion.jpg', 'Magia de fusión para caballeros gema.', 10);

-- Estados de carta
INSERT IGNORE INTO card_status (card_id, status) VALUES
(1, 'Limited'),
(3, 'Forbidden');

-- Colección de usuario
INSERT IGNORE INTO user_card_collection (user_id, card_id, quantity) VALUES
(3, 1, 2),
(3, 5, 1);

-- Crear mazo
INSERT IGNORE INTO decks (id, user_id, name, description, image) VALUES
(1, 3, 'Dragon Fury', 'Mazo centrado en dragones agresivos.', 'dragon_fury.jpg');

-- Cartas en el mazo
INSERT IGNORE INTO deck_cards (deck_id, card_id, quantity) VALUES
(1, 1, 2),
(1, 2, 1);
