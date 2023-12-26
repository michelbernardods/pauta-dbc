package com.pautadbc.service;

import com.pautadbc.model.Pauta;
import com.pautadbc.model.Voto;
import com.pautadbc.repository.PautaRepository;
import com.pautadbc.repository.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.pautadbc.validador.Validador.validarCPF;

@Service
public class PautaService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;

    public PautaService(PautaRepository pautaRepository, VotoRepository votoRepository) {
        this.pautaRepository = pautaRepository;
        this.votoRepository = votoRepository;
    }

    public void novaPauta(Pauta pauta) {
        pautaRepository.save(pauta);
    }

    public void abrirPauta(UUID id, int minutos) {
        Pauta pauta = pautaRepository.findById(id);
        if (pauta.getId() == null) {
            logger.info("Pauta não existe");
        }

        LocalDateTime inicio = LocalDateTime.now().plusMinutes(minutos);
        pauta.setInicio(inicio);
        pautaRepository.save(pauta);
    }


    public ResponseEntity<Object> registrarVoto(UUID idPauta, String idAssociado, String voto, String cpf) {
        Pauta pauta = pautaRepository.findById(idPauta);
        if (pauta.getId() == null) {
            logger.info("Pauta não existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pauta não existe.");
        }

        if (!validarCPF(cpf)) {
            return ResponseEntity.status(HttpStatus.OK).body("CPF invalido");
        }

        LocalDateTime agora = LocalDateTime.now();
        if (pauta.getFim() != null && agora.isAfter(pauta.getInicio())) {
            logger.info("Pauta encerrada");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pauta encerrada.");
        }

        boolean cpfJaVotou = votoRepository.existsByPautaIdAndCpf(idPauta, cpf);
        if (cpfJaVotou) {
            logger.info("Esse CPF já votou nesta pauta");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse CPF já votou nesta pauta.");
        }

        if (Objects.equals(voto, "sim") || Objects.equals(voto, "não")) {
            Voto novoVoto = new Voto();
            novoVoto.setIdAssociado(idAssociado);
            novoVoto.setPauta(pauta);
            novoVoto.setVoto(voto);
            novoVoto.setCpf(cpf);
            votoRepository.save(novoVoto);

            LocalDateTime fim = LocalDateTime.now();
            pauta.setFim(fim);
            pautaRepository.save(pauta);

            return ResponseEntity.status(HttpStatus.OK).body("Voto registrado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Voto inválido.");
        }
    }

    public long contarVotosPorPauta(UUID idPauta, String voto) {
        return votoRepository.countByPautaIdAndVoto(idPauta, voto);
    }

    public String obterResultadoVotacao(UUID idPauta) {
        long votosSim = contarVotosPorPauta(idPauta, "sim");
        long votosNao = contarVotosPorPauta(idPauta, "não");

        return "Resultado da votação - Sim: " + votosSim + ", Não: " + votosNao;
    }


}
