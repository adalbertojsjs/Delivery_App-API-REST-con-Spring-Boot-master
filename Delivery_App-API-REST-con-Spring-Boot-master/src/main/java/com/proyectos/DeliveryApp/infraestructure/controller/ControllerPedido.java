package com.proyectos.DeliveryApp.infraestructure.controller;

import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.domain.model.Pedido;
import com.proyectos.DeliveryApp.infraestructure.entity.PedidoEntity;
import com.proyectos.DeliveryApp.domain.ports.in.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/pedidos")
public class ControllerPedido {

    private final PedidoService service;

    public ControllerPedido(PedidoService service){
        this.service = service;
    }


    @Operation(summary = "Lista de pedidos",
            description = "Retorna un lista de pedidos del sistema")
    @ApiResponses({@ApiResponse(responseCode = "200",
            description = "Lista de pedidos obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<List<Pedido>> listar(){
        return ResponseEntity.ok(service.listar());//200.ok
    }



    @Operation(summary = "Crear pedido",
            description = "Crear pedido con los Datos ingresados")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno  del sistema ")})
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(pedido));//201
    }



    @Operation(summary = "Eliminar pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id){
        service.cancelar(id);
        return ResponseEntity.noContent().build();//204
    }


    @Operation(summary = "Actualizar estado del pedido",
            description = "Actualizar pedido con los Cambios ingresados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualización completada con existo"),
            @ApiResponse(responseCode = "400" , description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado"),
            @ApiResponse(responseCode = "500" , description = "Error interno del sistema")})
    @PatchMapping("/{id}/estadoPedido")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Long id ,
                                                         @RequestParam EstadoPedido estadoPedido){
        return ResponseEntity.ok(service.cambiarEstado(id, estadoPedido));
    }



    @Operation(summary = "Asignar repartidor",
            description = "Asignar repartidores a los pedidos por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignación completada con existo"),
            @ApiResponse(responseCode = "400" , description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado"),
            @ApiResponse(responseCode = "500" , description = "Error interno del sistema")})
    @PatchMapping("/{id}/repartidor")
    public ResponseEntity<Pedido> asignarPedido(@PathVariable Long id
            , @RequestParam Long repartidor){

        //respuesta http  -  el cuerpo de la respuesta
        return ResponseEntity.ok().body(service.asignarRepartidor(id, repartidor));//200.ok

    }



    @Operation(summary = "Pedido por restaurante",description = "Retorna los pedidos de X restaurante")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")})
    @GetMapping(params = "restauranteId")
    public ResponseEntity<List<Pedido>> listarPor_Restaurante(@RequestParam Long restauranteId){
        return  ResponseEntity.ok(service.obtenerPedidosPorRestaurante(restauranteId));//200.ok
    }



    @Operation(summary = "Pedido por cliente",description = "Retorna los pedidos de X clientes")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")})
    @GetMapping(params = "clienteId")
    public ResponseEntity<List<Pedido>> listarPor_Cliente(@RequestParam Long clienteId){
        return ResponseEntity.ok(service.obtenerPedidosPorCliente(clienteId));//200.ok
    }

    @Operation(summary = "Pedido por estado",description = "Retorna los pedidos de X estado (EN_CAMINO,CANCELADO)")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado con ese estado")})
    @GetMapping(params = "estado")
    public ResponseEntity<List<Pedido>> listarPor_Estado(@RequestParam EstadoPedido estado){
        return ResponseEntity.ok(service.obtenerPorEstado(estado));
    }


    @Operation(summary = "Pedido por ID",description = "Retorna los pedido por IDs")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado con ese ID")})
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = service.buscarPorId(id);
//        PedidoDTO pedidoDTO = PedidoMapper.toDTO(pedido);/TODO agregar los dto ya arrreglados
        return ResponseEntity.ok(pedido);//200.ok
    }

    //URL para llamar los metodos
    //GET /v1/pedidos
    //GET /v1/pedidos?estado=EN_CAMINO
    //GET /v1/pedidos?clienteId=5
    //GET /v1/pedidos?restauranteId=3
    //PUT /v1/pedidos/10/estado?estado=ENTREGADO
    //PUT /v1/pedidos/10/repartidor?repartidorId=7
    //DELETE /v1/pedidos/10

}
