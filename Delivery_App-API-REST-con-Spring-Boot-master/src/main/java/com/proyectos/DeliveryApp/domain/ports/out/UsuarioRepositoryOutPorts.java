package com.proyectos.DeliveryApp.domain.ports.out;

import com.proyectos.DeliveryApp.domain.enums.Rol;
import com.proyectos.DeliveryApp.domain.model.Usuario;

import java.util.List;

public interface UsuarioRepositoryOutPorts extends BaseCrudRepositoryOutPort<Usuario, Long> {

    List<Usuario> findByRol(Rol rol);

}
