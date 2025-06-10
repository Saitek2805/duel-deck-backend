-- Roles
CREATE TABLE IF NOT EXISTS `roles`  (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) UNIQUE NOT NULL
);

-- Usuarios
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(50) UNIQUE NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `enabled` BOOLEAN NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `image` VARCHAR(255)
);

-- Relación usuarios-roles
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
);

-- Expansiones
CREATE TABLE IF NOT EXISTS `expansions` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `code` VARCHAR(10) UNIQUE NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `release_date` DATE,
  `description` VARCHAR(255),
  `image` VARCHAR(255)
);

-- Sobres (1 a 1 con expansión)
CREATE TABLE IF NOT EXISTS `packs` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `expansion_id` INT UNIQUE NOT NULL,
  `image` VARCHAR(255),
  `description` VARCHAR(255),
  FOREIGN KEY (`expansion_id`) REFERENCES `expansions` (`id`) ON DELETE CASCADE
);

-- Tipos generales de cartas
CREATE TABLE IF NOT EXISTS `card_types` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) UNIQUE NOT NULL
);

-- Rarezas
CREATE TABLE IF NOT EXISTS `card_rarities` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) UNIQUE NOT NULL
);

-- Tipados
CREATE TABLE IF NOT EXISTS `card_typings` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) UNIQUE NOT NULL
);

-- Atributos
CREATE TABLE IF NOT EXISTS `card_attributes` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(20) UNIQUE NOT NULL
);

-- Cartas
CREATE TABLE IF NOT EXISTS `cards` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `code` VARCHAR(10) UNIQUE NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `type_id` INT,
  `typing_id` INT,
  `attribute_id` INT,
  `level` INT,
  `attack` INT,
  `defense` INT,
  `rarity_id` INT,
  `image` VARCHAR(255),
  `description` VARCHAR(255),
  `expansion_id` INT NOT NULL,
  FOREIGN KEY (`type_id`) REFERENCES `card_types` (`id`),
  FOREIGN KEY (`typing_id`) REFERENCES `card_typings` (`id`),
  FOREIGN KEY (`attribute_id`) REFERENCES `card_attributes` (`id`),
  FOREIGN KEY (`rarity_id`) REFERENCES `card_rarities` (`id`),
  FOREIGN KEY (`expansion_id`) REFERENCES `expansions` (`id`) ON DELETE CASCADE
);

-- Estado limitado/prohibido de cartas
CREATE TABLE IF NOT EXISTS `card_status` (
  `card_id` INT PRIMARY KEY,
  `status` ENUM ('Forbidden', 'Limited', 'Semi_Limited') NOT NULL,
  FOREIGN KEY (`card_id`) REFERENCES `cards` (`id`) ON DELETE CASCADE
);

-- Colección de cartas por usuario
CREATE TABLE IF NOT EXISTS `user_card_collection` (
  `user_id` INT NOT NULL,
  `card_id` INT NOT NULL,
  `quantity` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`user_id`, `card_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`card_id`) REFERENCES `cards` (`id`) ON DELETE CASCADE
);

-- Mazos
CREATE TABLE IF NOT EXISTS `decks` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(255),
  `image` VARCHAR(255),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

-- Cartas en cada mazo
CREATE TABLE IF NOT EXISTS `deck_cards` (
  `deck_id` INT NOT NULL,
  `card_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`deck_id`, `card_id`),
  FOREIGN KEY (`deck_id`) REFERENCES `decks` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`card_id`) REFERENCES `cards` (`id`) ON DELETE CASCADE
);

-- Comentarios en mazos
CREATE TABLE IF NOT EXISTS `deck_comments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `deck_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  FOREIGN KEY (`deck_id`) REFERENCES `decks` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

-- Comentarios en cartas
CREATE TABLE IF NOT EXISTS `card_comments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `card_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  FOREIGN KEY (`card_id`) REFERENCES `cards` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

-- Comentarios en expansiones
CREATE TABLE IF NOT EXISTS `expansion_comments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `expansion_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  FOREIGN KEY (`expansion_id`) REFERENCES `expansions` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);
