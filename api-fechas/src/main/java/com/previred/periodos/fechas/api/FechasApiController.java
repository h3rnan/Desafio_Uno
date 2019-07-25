package com.previred.periodos.fechas.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.previred.periodos.fechas.model.Periodo;
import com.previred.periodos.fechas.model.PeriodoSolucion;
import com.previred.periodos.fechas.service.FechaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    @ApiOperation(value = "Periodo y fechas faltantes", nickname = "getFechas", notes = "",
            response = PeriodoSolucion.class, tags={ "fechas", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operación Exitosa", response = PeriodoSolucion.class),
            @ApiResponse(code = 400, message = "Entrada Inválida") })
    @RequestMapping(value = "/fechas",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<PeriodoSolucion> getFechas(@ApiParam(value = "Periodo con lista de fechas")
                                                     @Valid @RequestBody Periodo periodo) {
        try {
            PeriodoSolucion detalle = fechaService.getPeriodoSolucion(periodo);
            return new ResponseEntity<>(detalle, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error obteniendo fechas", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
