package santander.desafio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import santander.desafio.dto.CepResponse;

/**
 * Implementação simplificada do serviço de consulta CEP via API externa
 * Usando RestTemplate ao invés de WebClient para evitar problemas de URL
 */
@Service
public class ViaCepApiService implements CepApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ViaCepApiService(@Value("${app.viacep.url:http://localhost:8089}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public CepResponse consultarCep(String cep) {
        try {
            // Remove caracteres não numéricos do CEP
            String cepLimpo = cep.replaceAll("[^0-9]", "");
            
            // Validação básica do CEP
            if (cepLimpo.length() != 8) {
                CepResponse response = new CepResponse();
                response.setErro(true);
                return response;
            }

            // Monta a URL completa
            String url = baseUrl + "/ws/" + cepLimpo + "/json/";

            // Consulta a API externa (Mockoon)
            CepResponse response = restTemplate.getForObject(url, CepResponse.class);

            return response != null ? response : createErrorResponse();

        } catch (Exception e) {
            // Em caso de erro, retorna resposta de erro
            return createErrorResponse();
        }
    }

    private CepResponse createErrorResponse() {
        CepResponse response = new CepResponse();
        response.setErro(true);
        return response;
    }
}