package santander.desafio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santander.desafio.dto.CepResponse;
import santander.desafio.model.CepLog;
import santander.desafio.service.CepLogService;
import santander.desafio.service.CepService;

import java.util.List;

/**
 * Controller REST para operações relacionadas a CEP
 * Aplicando Single Responsibility Principle - responsável apenas pela camada de apresentação
 */
@RestController
@RequestMapping("/api/cep")
@Tag(name = "CEP", description = "API para consulta de CEP e gerenciamento de logs")
public class CepController {

    private final CepService cepService;
    private final CepLogService cepLogService;

    @Autowired
    public CepController(CepService cepService, CepLogService cepLogService) {
        this.cepService = cepService;
        this.cepLogService = cepLogService;
    }

    @GetMapping("/{cep}")
    @Operation(summary = "Consultar CEP", description = "Consulta informações de um CEP e registra o log da consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CEP encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = santander.desafio.dto.ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "CEP inválido",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<santander.desafio.dto.ApiResponse<CepResponse>> consultarCep(
            @Parameter(description = "CEP a ser consultado (formato: 12345678 ou 12345-678)", example = "01310100")
            @PathVariable String cep) {

        try {
            CepResponse response = cepService.consultarCepComLog(cep);

            if (response.getErro() != null && response.getErro()) {
                return ResponseEntity.badRequest()
                        .body(santander.desafio.dto.ApiResponse.erro("CEP não encontrado ou inválido"));
            }

            return ResponseEntity.ok(santander.desafio.dto.ApiResponse.sucesso(response));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(santander.desafio.dto.ApiResponse.erro("Erro interno do servidor"));
        }
    }

    @GetMapping("/logs")
    @Operation(summary = "Listar todos os logs", description = "Retorna todos os logs de consulta de CEP")
    @ApiResponse(responseCode = "200", description = "Lista de logs retornada com sucesso")
    public ResponseEntity<santander.desafio.dto.ApiResponse<List<CepLog>>> listarLogs() {
        List<CepLog> logs = cepLogService.buscarTodosLogs();
        return ResponseEntity.ok(santander.desafio.dto.ApiResponse.sucesso(logs));
    }

    @GetMapping("/logs/{cep}")
    @Operation(summary = "Listar logs por CEP", description = "Retorna todos os logs de consulta para um CEP específico")
    @ApiResponse(responseCode = "200", description = "Lista de logs para o CEP retornada com sucesso")
    public ResponseEntity<santander.desafio.dto.ApiResponse<List<CepLog>>> listarLogsPorCep(
            @Parameter(description = "CEP para filtrar os logs", example = "01310100")
            @PathVariable String cep) {
        List<CepLog> logs = cepLogService.buscarLogsPorCep(cep);
        return ResponseEntity.ok(santander.desafio.dto.ApiResponse.sucesso(logs));
    }
}