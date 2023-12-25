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
import java.util.List;
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


}
