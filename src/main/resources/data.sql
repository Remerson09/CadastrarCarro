CREATE TABLE veiculo (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         marca VARCHAR(255) NOT NULL,
                         modelo VARCHAR(255) NOT NULL,
                         preco DECIMAL(10,2) NOT NULL,
                         anoFabricacao INT NOT NULL
);

