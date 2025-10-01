-- ===================================================================
-- DESAFIO SANTANDER - INICIALIZAÇÃO DO BANCO MYSQL
-- ===================================================================

-- Criar database se não existir
CREATE DATABASE IF NOT EXISTS cep_database 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

-- Usar o database
USE cep_database;

-- Criar usuário se não existir (já é criado pelo docker-compose, mas garantindo)
-- CREATE USER IF NOT EXISTS 'cepuser'@'%' IDENTIFIED BY 'ceppass123';
-- GRANT ALL PRIVILEGES ON desafio_cep.* TO 'cepuser'@'%';
-- FLUSH PRIVILEGES;

-- Criar tabela de logs de CEP
CREATE TABLE IF NOT EXISTS cep_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(10) NOT NULL,
    consulta_timestamp TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    sucesso BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Campos de resposta da API
    logradouro VARCHAR(255),
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    localidade VARCHAR(255),
    uf VARCHAR(2),
    ibge VARCHAR(10),
    gia VARCHAR(10),
    ddd VARCHAR(3),
    siafi VARCHAR(10),
    
    -- Campo para armazenar mensagens de erro
    erro_mensagem TEXT,
    
    -- Índices para otimizar consultas
    INDEX idx_cep (cep),
    INDEX idx_timestamp (consulta_timestamp),
    INDEX idx_sucesso (sucesso)
) ENGINE=InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Inserir alguns dados de exemplo (opcional)
INSERT INTO cep_logs (cep, logradouro, bairro, localidade, uf, sucesso) 
VALUES 
  ('01310100', 'Avenida Paulista', 'Bela Vista', 'São Paulo', 'SP', TRUE),
  ('04567890', 'Rua Exemplo', 'Vila Madalena', 'São Paulo', 'SP', TRUE)
ON DUPLICATE KEY UPDATE id=id;

-- Mostrar estatísticas
SELECT 'Database cep_database inicializado com sucesso!' as status;
SELECT COUNT(*) as total_logs FROM cep_logs;