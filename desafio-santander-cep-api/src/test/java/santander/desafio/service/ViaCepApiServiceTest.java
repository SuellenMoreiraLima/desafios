package santander.desafio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import santander.desafio.dto.CepResponse;

import static org.junit.jupiter.api.Assertions.*;

class ViaCepApiServiceTest {

    private ViaCepApiService viaCepApiService;

    @BeforeEach
    void setUp() {
        viaCepApiService = new ViaCepApiService("http://localhost:8089");
    }

    @Test
    void testConsultarCepComCepInvalido() {
        CepResponse response = viaCepApiService.consultarCep("123");

        assertNotNull(response);
        assertTrue(response.getErro());
    }

    @Test
    void testConsultarCepComCepVazio() {
        CepResponse response = viaCepApiService.consultarCep("");

        assertNotNull(response);
        assertTrue(response.getErro());
    }

    @Test
    void testConsultarCepComCepComCaracteresEspeciais() {
        CepResponse response = viaCepApiService.consultarCep("01310-100");
        assertNotNull(response);
    }
}