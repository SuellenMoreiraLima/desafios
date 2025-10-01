package santander.desafio.service;

import santander.desafio.dto.CepResponse;

/**
 * Interface principal do serviço de CEP
 * Aplicando o Single Responsibility Principle do SOLID
 */
public interface CepService {
    CepResponse consultarCepComLog(String cep);
}