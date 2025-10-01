package santander.desafio.service;

import santander.desafio.dto.CepResponse;

/**
 * Interface para consulta de CEP em API externa
 * Aplicando o Dependency Inversion Principle do SOLID
 */
public interface CepApiService {
    CepResponse consultarCep(String cep);
}