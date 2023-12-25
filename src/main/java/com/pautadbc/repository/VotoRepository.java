package com.pautadbc.repository;

import com.pautadbc.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
}
