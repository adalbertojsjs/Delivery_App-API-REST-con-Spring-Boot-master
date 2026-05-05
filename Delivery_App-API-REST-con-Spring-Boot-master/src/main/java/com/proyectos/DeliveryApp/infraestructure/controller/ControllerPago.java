package com.proyectos.DeliveryApp.infraestructure.controller;


import com.proyectos.DeliveryApp.domain.ports.in.PagoService;
import com.proyectos.DeliveryApp.infraestructure.http.dto.request.PagoRequest;
import com.proyectos.DeliveryApp.infraestructure.http.dto.response.PagoResponse;
import com.proyectos.DeliveryApp.infraestructure.mapper.PagoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v2/pago")
public class ControllerPago {

    private final PagoService service;

    private final PagoMapper mapper;



    @Operation(summary = "Pagar Pedido",
            description = "Pagar pedidos por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago completado con existo"),
            @ApiResponse(responseCode = "400" , description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado"),
            @ApiResponse(responseCode = "500" , description = "Error interno del sistema")})
    @PutMapping("/{id}/pagar")
    public ResponseEntity<PagoResponse> pagarPedido(@PathVariable Long id, @RequestBody PagoRequest request){
        var domain = service.Pagar(id, mapper.requestToDomain(request));
        return ResponseEntity.ok().body(mapper.toResponse(domain));//200.ok

    }


    @Operation(summary = "Pago por ID",description = "Retorna los Pago por IDs")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado con ese ID")})
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> buscarPorId(@PathVariable Long id) {
        var pago = service.buscarPagoId(id);
        return ResponseEntity.ok(mapper.toResponse(pago));//200.ok
    }
}
