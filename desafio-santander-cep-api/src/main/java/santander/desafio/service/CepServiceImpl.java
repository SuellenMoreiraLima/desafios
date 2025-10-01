package santander.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santander.desafio.dto.CepResponse;
import santander.desafio.model.CepLog;

/**
 * Implementação principal do serviço de CEP
 * Aplicando:
 * - Single Responsibility Principle: coordena a consulta e o log
 * - Dependency Inversion Principle: depende de abstrações, não de implementações
 * - Open/Closed Principle: aberto para extensão via injeção de dependência
 */
@Service
public class CepServiceImpl implements CepService {

    private final CepApiService cepApiService;
    private final CepLogService cepLogService;

    @Autowired
    public CepServiceImpl(CepApiService cepApiService, CepLogService cepLogService) {
        this.cepApiService = cepApiService;
        this.cepLogService = cepLogService;
    }

    @Override
    public CepResponse consultarCepComLog(String cep) {
        CepLog log = new CepLog(cep);

        try {
            CepResponse response = cepApiService.consultarCep(cep);

            if (response.getErro() != null && response.getErro()) {
                log.setSucesso(false);
                log.setErroMensagem("CEP não encontrado ou inválido");
            } else {
                log.setSucesso(true);
                log.setLogradouro(response.getLogradouro());
                log.setComplemento(response.getComplemento());
                log.setBairro(response.getBairro());
                log.setLocalidade(response.getLocalidade());
                log.setUf(response.getUf());
                log.setIbge(response.getIbge());
                log.setGia(response.getGia());
                log.setDdd(response.getDdd());
                log.setSiafi(response.getSiafi());
            }

            cepLogService.salvarLog(log);

            return response;

        } catch (Exception e) {
            log.setSucesso(false);
            log.setErroMensagem("Erro interno do servidor: " + e.getMessage());
            cepLogService.salvarLog(log);

            CepResponse errorResponse = new CepResponse();
            errorResponse.setErro(true);
            return errorResponse;
        }
    }
}