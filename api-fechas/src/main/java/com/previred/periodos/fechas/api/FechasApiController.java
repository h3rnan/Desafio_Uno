package com.previred.periodos.fechas.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.previred.periodos.fechas.model.Periodo;
import com.previred.periodos.fechas.model.PeriodoSolucion;
import com.previred.periodos.fechas.service.FechaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class FechasApiController implements FechasApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private FechaService fechaService;

    @org.springframework.beans.factory.annotation.Autowired
    public FechasApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<PeriodoSolucion> getFechas(Periodo periodo) {
        try {
            PeriodoSolucion detalle = fechaService.getPeriodoSolucion(periodo);
            return new ResponseEntity<>(detalle, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error obteniendo fechas", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
