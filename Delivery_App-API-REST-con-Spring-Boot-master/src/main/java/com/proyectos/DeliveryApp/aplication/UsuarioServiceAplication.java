package com.proyectos.DeliveryApp.aplication;

import com.proyectos.DeliveryApp.domain.Exception.UsuarioNoEncontradoException;
import com.proyectos.DeliveryApp.domain.model.Usuario;
import com.proyectos.DeliveryApp.domain.ports.out.UsuarioRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.enums.Rol;
import com.proyectos.DeliveryApp.domain.ports.in.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UsuarioServiceAplication implements UsuarioService {

  private final UsuarioRepositoryOutPorts usuarioRepository;


    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }



    @Override
    public Usuario crear(Usuario usuario) {
        if (usuario.getId() != null){
            throw new IllegalArgumentException("El ID no debe ser enviado");
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizarRol(Long id, Rol rolActualizado) {
        if (id == null ){
            throw  new IllegalArgumentException("El ID es obligatorio");
        }

        if (rolActualizado == null){
            throw new IllegalArgumentException("El ROL es obligatorio");
        }

        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(() -> new UsuarioNoEncontradoException(id));

        if (usuario.getRol() == rolActualizado) {
            throw new IllegalStateException("El usuario ya tiene ese rol");
        }


        usuario.setRol(rolActualizado);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarRol(Rol rol) {
        if (rol == null){
            throw new IllegalArgumentException("El ROL es obligatorio");
        }
        return usuarioRepository.findByRol(rol);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        if (id == null){
            throw new IllegalArgumentException("El ID es obligatorio");
        }
         return usuarioRepository.
                findById(id).orElseThrow(() -> new UsuarioNoEncontradoException(id));
    }
}
