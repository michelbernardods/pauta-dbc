package com.pautadbc.service;

import com.pautadbc.model.Pauta;
import com.pautadbc.repository.PautaRepository;
import com.pautadbc.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotoRepository votoRepository;

    @InjectMocks
    private PautaService pautaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Captor
    private ArgumentCaptor<Pauta> pautaCaptor;

    @Test
    void testNovaPauta() {
        Pauta pauta = new Pauta();
        pautaService.novaPauta(pauta);

        verify(pautaRepository, times(1)).save(pauta);
    }
    @Test
    void testAbrirPauta() {
        UUID idPauta = UUID.randomUUID();
        int minutos = 10;
        Pauta pauta = new Pauta();
        LocalDateTime now = LocalDateTime.now();

        when(pautaRepository.findById(idPauta)).thenReturn(pauta);
        pautaService.abrirPauta(idPauta, minutos);

        verify(pautaRepository, times(1)).findById(idPauta);
        verify(pautaRepository, times(1)).save(pautaCaptor.capture());

        LocalDateTime savedInicio = pautaCaptor.getValue().getInicio();
        Duration difference = Duration.between(savedInicio, now.plusMinutes(minutos));
        assertTrue(difference.getSeconds() < 1); // Verificar se a diferença é menor que 1 segundo
    }

    @Test
    void testContarVotosPorPauta() {
        UUID idPauta = UUID.randomUUID();
        when(votoRepository.countByPautaIdAndVoto(idPauta, "sim")).thenReturn(5L);

        long count = pautaService.contarVotosPorPauta(idPauta, "sim");

        verify(votoRepository, times(1)).countByPautaIdAndVoto(idPauta, "sim");
        assertEquals(5L, count);
    }

    @Test
    void testObterResultadoVotacao() {
        UUID idPauta = UUID.randomUUID();
        when(votoRepository.countByPautaIdAndVoto(idPauta, "sim")).thenReturn(5L);
        when(votoRepository.countByPautaIdAndVoto(idPauta, "não")).thenReturn(3L);

        String resultado = pautaService.obterResultadoVotacao(idPauta);

        verify(votoRepository, times(1)).countByPautaIdAndVoto(idPauta, "sim");
        verify(votoRepository, times(1)).countByPautaIdAndVoto(idPauta, "não");

        assertEquals("Resultado da votação - Sim: 5, Não: 3", resultado);
    }
}
