package com.proyectos.DeliveryApp.domain.ports.in;

import com.proyectos.DeliveryApp.domain.model.Usuario;
import com.proyectos.DeliveryApp.infraestructure.entity.UsuarioEntity;
import com.proyectos.DeliveryApp.domain.enums.Rol;

import java.util.List;

public interface UsuarioService {

    Usuario crear(Usuario usuario);

    Usuario actualizarRol(Long id, Rol rolActualizado);

    List<Usuario> listarRol(Rol rol);

    Usuario buscarPorId(Long id);

    List<Usuario> listar();


}
