package com.proyectos.DeliveryApp.infraestructure.controller;

import com.proyectos.DeliveryApp.domain.model.Restaurante;
import com.proyectos.DeliveryApp.domain.ports.in.ServiceRestaurante;
import com.proyectos.DeliveryApp.infraestructure.http.dto.response.RestauranteResponse;
import com.proyectos.DeliveryApp.infraestructure.mapper.RestauranteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v2/restaurantes")
    public class ControllerRestaurante {

        private final ServiceRestaurante service;
        private final RestauranteMapper mapper;


    // LISTAR
    @Operation(summary = "Lista de restaurantes", description = "Retorna un lista de restaurantes del sistema")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Lista de restaurantes obtenida correctamente"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<List<Restaurante>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    // CREAR
    @Operation(summary = "Crear restaurantes", description = "Crear restaurantes con los Datos ingresados")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Restaurante creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno  del sistema ")})
    @PostMapping
    public ResponseEntity<RestauranteResponse> crear(@RequestBody Restaurante restaurante){
        var domain = service.crear(restaurante);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.domainToResponse(domain));
    }

    // ACTUALIZAR
    @Operation(summary = "Actualizar restaurante", description = "Actualizar restaurante con los Cambios ingresados")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Actualización completada con existo"),
            @ApiResponse(responseCode = "400" , description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado"),
            @ApiResponse(responseCode = "500" , description = "Error interno del sistema")})
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponse> actualizar(@PathVariable Long id, @RequestBody Restaurante restaurante){
        var domain = service.actualizar(id, restaurante);
        return ResponseEntity.ok(mapper.domainToResponse(domain));
    }

    // ELIMINAR
    @Operation(summary = "Eliminar restaurante  ")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Id del restaurante", example = "1")
            @PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.noContent().build(); // 204
    }
}



