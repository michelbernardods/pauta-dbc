package com.pautadbc.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;
@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;
    private String idAssociado;
    private String voto;

    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public String getIdAssociado() {
        return idAssociado;
    }

    public void setIdAssociado(String idAssociado) {
        this.idAssociado = idAssociado;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voto voto1 = (Voto) o;
        return Objects.equals(id, voto1.id) && Objects.equals(pauta, voto1.pauta) && Objects.equals(idAssociado, voto1.idAssociado) && Objects.equals(voto, voto1.voto) && Objects.equals(cpf, voto1.cpf);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, pauta, idAssociado, voto, cpf);
    }

    @Override
    public String toString() {
        return "Voto{" +
                "id=" + id +
                ", pauta=" + pauta +
                ", idAssociado='" + idAssociado + '\'' +
                ", voto='" + voto + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
