package santander.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santander.desafio.model.CepLog;
import santander.desafio.repository.CepLogRepository;

import java.util.List;

/**
 * Implementação do serviço de log de consultas CEP
 * Aplicando Single Responsibility Principle - responsável apenas pelo gerenciamento de logs
 */
@Service
public class CepLogServiceImpl implements CepLogService {

    private final CepLogRepository cepLogRepository;

    @Autowired
    public CepLogServiceImpl(CepLogRepository cepLogRepository) {
        this.cepLogRepository = cepLogRepository;
    }

    @Override
    public CepLog salvarLog(CepLog log) {
        return cepLogRepository.save(log);
    }

    @Override
    public List<CepLog> buscarLogsPorCep(String cep) {
        return cepLogRepository.findByCepOrderByConsultaTimestampDesc(cep);
    }

    @Override
    public List<CepLog> buscarTodosLogs() {
        return cepLogRepository.findAll();
    }
}