package com.pautadbc.controller;

import com.pautadbc.model.Pauta;
import com.pautadbc.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


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
    public ResponseEntity<Object> abrirPauta(@RequestParam String id, @RequestParam int minutos) {
        UUID pautaId = UUID.fromString(id);
        service.abrirPauta(pautaId, minutos);
        return ResponseEntity.status(HttpStatus.OK).body("Pauta para votação aberta.");
    }
}