package com.pautadbc.controller;

import com.pautadbc.model.Pauta;
import com.pautadbc.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.pautadbc.validador.Validador.validarCPF;


@RestController
@RequestMapping("/api/v1")
public class PautaController {

    @Autowired
    public final PautaService service;

    public PautaController(PautaService service) {
        this.service = service;
    }

    @PostMapping("cadastrar")
    public ResponseEntity<UUID> cadastrar(@RequestBody Pauta pauta) {
        service.novaPauta(pauta);
        return ResponseEntity.status(HttpStatus.OK).body(pauta.getId());
    }

    @PostMapping("abrir")
    public ResponseEntity<Object> abrirPauta(@RequestParam("id") String id, @RequestParam(value = "minutos", required = false, defaultValue = "1") int minutos) {
        UUID pautaId = UUID.fromString(id);
        service.abrirPauta(pautaId, minutos);
        return ResponseEntity.status(HttpStatus.OK).body("Pauta para votação aberta.");
    }

    @PostMapping("votar")
    public ResponseEntity<Object> votar(@RequestParam UUID idPauta, String idAssociado, String voto, String cpf)  {
        return service.registrarVoto(idPauta, idAssociado, voto, cpf);
    }
    @GetMapping("resultado/{id}")
    public ResponseEntity<String> obterResultadoVotacao(@PathVariable String id) {
        UUID pautaId = UUID.fromString(id);
        String resultado = service.obterResultadoVotacao(pautaId);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
            String mensagem = "O nome da pauta já está cadastrado.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagem);
        }
    }
}