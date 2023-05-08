USE validador_ctd;
CREATE TABLE status_ctd (
    id                 INTEGER PRIMARY KEY AUTO_INCREMENT,
    gitHub             VARCHAR(255),
    observacoes        VARCHAR(255),
    pode_receber_badge BOOLEAN NOT NULL,
    qtd_dias_ctd       SMALLINT NOT NULL
);
