package com.proyectos.DeliveryApp.infraestructure.controller;


import com.proyectos.DeliveryApp.domain.model.Usuario;
import com.proyectos.DeliveryApp.domain.enums.Rol;
import com.proyectos.DeliveryApp.domain.ports.in.UsuarioService;
import com.proyectos.DeliveryApp.infraestructure.http.dto.response.UsuarioResponse;
import com.proyectos.DeliveryApp.infraestructure.mapper.UsuarioMapper;
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
@RequestMapping("/v2/usuarios")
public class ControllerUsuario {

   private final UsuarioService service;

   private final UsuarioMapper mapper;


    @Operation(summary = "Lista de usuario", description = "Retorna una lista de usuarios")
    @ApiResponses({@ApiResponse (responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
                    @ApiResponse(responseCode = "500",description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        return  ResponseEntity.ok(service.listar());
    }



    @Operation(summary = "Crea usuarios", description = "Crea un nuevo usuario y lo guarda en el sistema")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400" , description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor"),})
    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@RequestBody Usuario usuario){
        var domain = service.crear(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.domainToResponse(domain));

    }

    @Operation(summary = "Actualiza el rol del usuario", description = "Retorna el cambio de el rol del usuario por Id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Usuario ACTUALIZADO con exito"),
            @ApiResponse(responseCode = "400" , description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PatchMapping("{id}/rol")
    public ResponseEntity<UsuarioResponse> actualizarRol(@PathVariable Long id, @RequestParam Rol rol){
        var domain = service.actualizarRol(id , rol);
        return ResponseEntity.ok(mapper.domainToResponse(domain));
    }


    @Operation(summary = "lista de usuarios por Rol", description = "Retorno una lista de usuarios del Rol que se ingreso por parametros" )
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping(params = "rol")
    public ResponseEntity<List<Usuario>> listarPor_Rol(@RequestParam Rol rol){
        return ResponseEntity.ok(service.listarRol(rol));
    }


    @Operation(summary = " Retorna Usuario por Id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Usuario obtenido con exito"),
                 @ApiResponse(responseCode = "400" , description = "Datos inválidos"),
                 @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarId(@Parameter(description = "Id del usuario", example = "id = 1") @PathVariable Long id){
        var usuario = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.domainToResponse(usuario));

    }

    //Endpoints:

   // GET    /v1/usuarios
    //POST   /v1/usuarios
    //GET    /v1/usuarios/{id}
    //PATCH  /v1/usuarios/{id}/rol?rol=REPARTIDOR
    //GET    /v1/usuarios?rol=CLIENTE
}
