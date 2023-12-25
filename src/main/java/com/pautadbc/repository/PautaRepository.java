package com.pautadbc.repository;

import com.pautadbc.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    Pauta findById(UUID id);
}

