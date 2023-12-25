package com.pautadbc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Pauta  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public String getNome() {
        return nome;
    }

    private LocalDateTime inicio;
    private LocalDateTime fim;

    public Pauta() {

    }

    public Pauta(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private String nome;

    public Pauta setId(UUID id) {
        this.id = id;
        return null;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pauta pauta = (Pauta) o;
        return Objects.equals(id, pauta.id) && Objects.equals(nome, pauta.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Pauta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

}
