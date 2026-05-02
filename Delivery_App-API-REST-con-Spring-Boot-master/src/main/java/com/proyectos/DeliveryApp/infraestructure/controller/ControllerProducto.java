package com.proyectos.DeliveryApp.infraestructure.controller;


import com.proyectos.DeliveryApp.domain.model.Producto;
import com.proyectos.DeliveryApp.domain.enums.Disponible;
import com.proyectos.DeliveryApp.domain.ports.in.ProductoService;
import com.proyectos.DeliveryApp.infraestructure.http.dto.request.ProductoRequest;
import com.proyectos.DeliveryApp.infraestructure.http.dto.response.ProductoResponse;
import com.proyectos.DeliveryApp.infraestructure.mapper.ProductoMapper;
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
@RequestMapping("/v2/productos")
public class ControllerProducto {

    private final ProductoService service;

    private final ProductoMapper mapper;



    @Operation(summary = "Lista de productos",
            description = "Retorna un lista de productos del sistema")
    @ApiResponses({@ApiResponse(responseCode = "200",
            description = "Lista de productos obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        return ResponseEntity.ok(service.listar());//200.ok
    }



    @Operation(summary = "Crear productos",
            description = "Crear productos con los Datos ingresados")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Productos creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno  del sistema ")})
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody ProductoRequest request){
        var domain = service.crear(mapper.requestToDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.domainToRespose(domain));//201
    }


    @Operation(summary = "Actualizar disponibilidad del producto",
            description = "Actualizar producto con los Cambios ingresados")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Actualización completada con existo"),
            @ApiResponse(responseCode = "400" , description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado"),
            @ApiResponse(responseCode = "500" , description = "Error interno del sistema")})
    @PatchMapping("/{id}/disponible")
    public ResponseEntity<ProductoResponse> actualizarDisponibilidad(@PathVariable Long id,@RequestParam Disponible disponible){
        var domain = service.cambiarDisponibilidad(id,disponible);
        return ResponseEntity.ok().body(mapper.domainToRespose(domain));
    }

    @Operation(summary = "Producto por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> buscarId(@Parameter(description = "Id del producto", example = "1") @PathVariable Long id){
        var producto = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.domainToRespose(producto));
    }


    @Operation(summary = "Producto por restaurante",description = "Retorna los productos de X restaurante")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")})
    @GetMapping( params = "restauranteId")
    public ResponseEntity<List<Producto>> listarPor_Restaurante(@RequestParam Long restauranteId){
        return ResponseEntity.ok(service.listarPorRestaurante(restauranteId));
    }
    //URL para las peticiones HTTP
    //GET    /v1/pedidos
    //POST   /v1/pedidos
    //DELETE /v1/pedidos/{id}
    //
    //PUT    /v1/pedidos/{id}/estado?estado=EN_CAMINO
    //PUT    /v1/pedidos/{id}/repartidor?repartidorId=7
    //
    //GET    /v1/pedidos?estado=ENTREGADO
    //GET    /v1/pedidos?clienteId=5
    //GET    /v1/pedidos?restauranteId=3
}
