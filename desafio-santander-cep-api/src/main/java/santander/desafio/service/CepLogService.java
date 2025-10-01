package santander.desafio.service;

import santander.desafio.model.CepLog;

import java.util.List;

/**
 * Interface para gerenciamento de logs de consulta CEP
 * Aplicando o Dependency Inversion Principle do SOLID
 */
public interface CepLogService {
    CepLog salvarLog(CepLog log);
    List<CepLog> buscarLogsPorCep(String cep);
    List<CepLog> buscarTodosLogs();
}