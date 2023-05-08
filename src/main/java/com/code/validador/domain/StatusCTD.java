package com.code.validador.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "status_ctd")
public class StatusCTD  extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    public Long id;
    public String gitHub = "";
    public short qtd_dias_ctd = 0;
    public boolean pode_receber_badge = false;
    public String observacoes = "";

    public StatusCTD() {}

    public StatusCTD(String gitHub, short qtd_dias_ctd) {
        this.gitHub = gitHub;
        this.qtd_dias_ctd = qtd_dias_ctd;
    }

    @Override
    public String toString() {
        return "StatusCTD{" +
                "gitHub='" + gitHub + '\'' +
                ", qtd_dias_ctd=" + qtd_dias_ctd +
                ", pode_receber_badge=" + pode_receber_badge +
                ", observacoes='" + observacoes + '\'' +
                '}';
    }
}
