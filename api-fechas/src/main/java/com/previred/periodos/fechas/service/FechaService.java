package com.previred.periodos.fechas.service;

import com.previred.periodos.fechas.model.Periodo;
import com.previred.periodos.fechas.model.PeriodoSolucion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FechaService {

    Logger log = LoggerFactory.getLogger(FechaService.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("${previred.gdd.endpoint}")
    private String gddEndpoint;

    public PeriodoSolucion getPeriodoSolucion(Periodo periodo) {
        if (periodo.getId() == null) {
            periodo = restTemplate.getForObject(gddEndpoint, Periodo.class);
        }
        PeriodoSolucion solucion = new PeriodoSolucion();
        solucion.setId(periodo.getId());
        solucion.setFechaCreacion(periodo.getFechaCreacion());
        solucion.setFechaFin(periodo.getFechaFin());
        solucion.setFechas(periodo.getFechas());
        solucion.setFechasFaltantes(obtenerFechasFaltantes(periodo));
        return solucion;
    }

    private List<LocalDate> obtenerFechasFaltantes(Periodo periodo) {
        List<LocalDate> ret = new ArrayList<>();
        Stream<LocalDate> fechas = periodo.getFechas().stream();
        Diferencia<LocalDate> dateDiferencia = fechas.collect(Diferencia::new,
            (diferencia, localDate) -> {
                LocalDate last = diferencia.getProcessed().isEmpty() ? null :
                        diferencia.getProcessed().get(diferencia.getProcessed().size() - 1);
                if (last != null) {
                    while (last.isBefore(localDate)) {
                        diferencia.getMissing().add(last.plusMonths(1));
                    }
                }
                diferencia.getProcessed().add(localDate);
            },
            (d1, d2) -> {});
        return dateDiferencia.getMissing();
    }

    private static class Diferencia<T> {
        private ArrayList<T> processed;
        private ArrayList<T> missing;

        public Diferencia() {
            this.processed = new ArrayList<>();
            this.missing = new ArrayList<>();
        }

        public ArrayList<T> getProcessed() {
            return this.processed;
        }

        public ArrayList<T> getMissing() {
            return this.missing;
        }
    }

}
