package com.proyectos.DeliveryApp;

import com.proyectos.DeliveryApp.aplication.UsuarioServiceAplication;
import com.proyectos.DeliveryApp.domain.enums.Rol;
import com.proyectos.DeliveryApp.domain.model.Usuario;
import com.proyectos.DeliveryApp.domain.ports.out.UsuarioRepositoryOutPorts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestUsuarioServiceAplication {


    @Mock
    UsuarioRepositoryOutPorts repository;

    @InjectMocks
    UsuarioServiceAplication aplication;

    //FinAll Users
    @Test
    void shouldGetAlLUsersSuccessfully(){

        List<Usuario> lista = List.of(Usuario.
                        builder().
                        id(1L).
                        nombre("Random333").
                        email("Random444").
                        contrasena("Random888").
                        rol(Rol.CLIENTE).
                        build(),
                Usuario.
                builder().
                id(1L).
                nombre("Random344").
                email("Random44554").
                contrasena("Random855588").
                rol(Rol.CLIENTE).build());

        when(repository.findAll()).thenReturn(lista);

        var result = aplication.listar();
        assertNotNull(result);
        assertEquals("Random444",result.getFirst().getEmail());
        assertEquals("Random44554", result.getLast().getEmail());
        assertEquals(2, result.size());

        verify(repository).findAll();
    }

    //Create Users
    @Test
    void createUsersSuccessfully(){

        var user =   Usuario.
                builder().
                nombre("Random344").
                email("Random44554").
                contrasena("Random855588").
                rol(Rol.CLIENTE).build();


        when(repository.save(user)).thenReturn(user);

        var result = aplication.crear(user);
        assertNotNull(result);
        assertEquals("Random344", result.getNombre());
        assertEquals("Random44554", result.getEmail());


        verify(repository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsProvided(){

        var user =   Usuario.
                builder().
                id(1L).
                nombre("Random344").
                email("Random44554").
                contrasena("Random855588").
                rol(Rol.CLIENTE).build();

        assertThrows(IllegalArgumentException.class,()-> aplication.crear(user));

        verify(repository, never()).save(any());
    }

    //Update Rol users

    @Test
    void shouldUpdateTheUserRoleSuccessfully(){
        var user =   Usuario.
                builder().
                id(1L).
                nombre("Random344").
                email("Random44554").
                contrasena("Random855588").
                rol(Rol.CLIENTE).build();

        when(repository.save(user)).thenReturn(user);
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        var result = aplication.actualizarRol(user.getId(),Rol.REPARTIDOR);

        assertNotNull(result);
        assertEquals("Random344",result.getNombre());
        assertEquals(Rol.REPARTIDOR,result.getRol());

        verify(repository).save(user);
        verify(repository).findById(user.getId());

    }

    @Test
    void shouldThrowExceptionWhenIdIsNull(){
        var user =   Usuario.
                builder().
                id(null).
                nombre("Random344").
                email("Random44554").
                contrasena("Random855588").
                rol(Rol.CLIENTE).build();

       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> aplication.actualizarRol(user.getId(),Rol.REPARTIDOR));

        log.info(exception.getMessage());
        assertEquals("El ID es obligatorio", exception.getMessage());
        verify(repository, never()).save(any());
        verify(repository, never()).findById(any());
    }

    @Test
    void shouldThrowExceptionWhenRoleIsNull(){
        var user =   Usuario.
                builder().
                id(1L).
                nombre("Random344").
                email("Random44554").
                contrasena("Random855588").
                rol(Rol.CLIENTE).build();

       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> aplication.actualizarRol(user.getId(),null));

        log.info(exception.getMessage());
        assertEquals("El ROL es obligatorio",exception.getMessage());
        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyHasThatRole(){
        var user =   Usuario.
                builder().
                id(1L).
                nombre("Random344").
                email("Random44554").
                contrasena("Random855588").
                rol(Rol.CLIENTE).build();

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

       IllegalStateException exception = assertThrows(IllegalStateException.class, ()
                -> aplication.actualizarRol(user.getId(),Rol.CLIENTE));

        log.info(exception.getMessage());
       assertEquals("El usuario ya tiene ese rol",exception.getMessage());
        verify(repository).findById(user.getId());
        verify(repository, never()).save(any());
    }


    //FindALL Users By Rol

    @Test
    void shouldGetAllUsersByRoleSuccessfully(){

        Rol rol = Rol.CLIENTE;
       List<Usuario> listaRol = List.of(Usuario.
               builder().
               id(1L).
               nombre("Random344").
               email("Random44554").
               contrasena("Random855588").
               rol(Rol.CLIENTE).build(),
                Usuario.
                builder().
                id(2L).
                nombre("Random35554").
                email("Random77884").
                contrasena("Random822288").
                rol(Rol.CLIENTE).build());

       when(repository.findByRol(rol)).thenReturn(listaRol);

       var result = aplication.listarRol(rol);

       assertNotNull(result);
       assertEquals("Random35554", result.getLast().getNombre());
       assertEquals("Random344",result.getFirst().getNombre());
       assertEquals(2,result.size());

       verify(repository).findByRol(rol);

    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenRoleIsNull(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()
                -> aplication.listarRol(null));

        log.info(exception.getMessage());
        assertEquals("El ROL es obligatorio",exception.getMessage());

        verify(repository, never()).findByRol(any());
    }

    //FindById users

    @Test
    void shouldGetUsertByIdSuccessfully(){
        var user =   Usuario.
                builder().
                id(1L).
                nombre("Random344").
                email("Random44554").
                contrasena("Random855588").
                rol(Rol.CLIENTE).build();

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        var result = aplication.buscarPorId(user.getId());

        assertNotNull(result);
        assertEquals(Rol.CLIENTE,result.getRol());
        assertEquals("Random855588", result.getContrasena());
        assertEquals("Random44554",result.getEmail());
        assertEquals("Random344",result.getNombre());

        verify(repository).findById(user.getId());
    }

    @Test
    void shouldThrowExceptionUserIdIsNull(){
        var user =   Usuario.
                builder().
                id(null).
                nombre("Random344").
                email("Random44554").
                contrasena("Random855588").
                rol(Rol.CLIENTE).build();

      IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
              ()-> aplication.buscarPorId(user.getId()));

      log.info(exception.getMessage());
      assertEquals("El ID es obligatorio",exception.getMessage());

      verify(repository,never()).findById(any());
    }
}