package com.example;

import io.quarkus.logging.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ValidadorCTD {

    public String iniciar(final String URL, final int qtdDiasParaValidar) {
        List<LocalDate> contribuicoesOrdenadas;
        try {
            contribuicoesOrdenadas = obterContribuicoes(URL).orElse(new ArrayList<>());
        } catch (Exception e) {
            return e.getMessage();
        }
        int count = 1;
        LocalDate ultimaDataDaSequencia = null;
        for (LocalDate data : contribuicoesOrdenadas) {
            if (ultimaDataDaSequencia != null && ChronoUnit.DAYS.between(ultimaDataDaSequencia, data) == 1) {
                count++;
                if (count == qtdDiasParaValidar) {
                    String retorno = """
                            As datas formam uma sequência de %d dias 
                            contínuos entre %s e %s.
                            """;
                    return String.format(retorno, qtdDiasParaValidar, data.minusDays(qtdDiasParaValidar - 1), data);
                }
            } else {
                count = 1;
            }
            ultimaDataDaSequencia = data;
        }
        if (count < qtdDiasParaValidar) return "As datas não formam uma sequência de " + qtdDiasParaValidar + " dias contínuos.";
        return "Não foi possível validar o período!!";
    }
    public Optional<List<LocalDate>> obterContribuicoes(final String URL) throws Exception {
        Log.info("Obtendo contribuicoes...");
        try {
            Log.info("Obtendo corpo html...");
            Document doc = Jsoup.connect(URL).get();
            Log.info("Corpo html obtido!");
            Log.info("Montando lista de dias com contribuicoes...");
            List<LocalDate> contribuicoesOrdenadas = doc.select("rect")
                    .stream()
                    .filter(element -> !element.childNodes().isEmpty() &&
                            !element.childNodes().get(0).attr("#text").startsWith("No") &&
                            LocalDate.parse(element.attr("data-date")).isBefore(LocalDate.now().plusDays(1))
                    ).collect(Collectors.toMap(
                            element -> LocalDate.parse(element.attr("data-date")),
                            element -> element.childNodes().get(0).attr("#text")))
                    .keySet()
                    .stream()
                    .sorted(LocalDate::compareTo)
                    .toList();
            Log.info("Lista de dias com contribuicoes montada!");
            return Optional.ofNullable(contribuicoesOrdenadas);
        } catch (IOException e) {
            throw new Exception("Não foi possível obter os dados da URL informada!!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new Exception("Não foi possível montar a lista de contrubuições!!");
        }
    }
}
