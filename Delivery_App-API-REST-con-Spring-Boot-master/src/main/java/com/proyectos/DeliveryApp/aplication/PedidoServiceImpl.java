package com.proyectos.DeliveryApp.aplication;

import com.proyectos.DeliveryApp.domain.Exception.PedidoNoEncontradoException;
import com.proyectos.DeliveryApp.domain.Exception.RestauranteNoEncontradoException;
import com.proyectos.DeliveryApp.domain.Exception.UsuarioNoEncontradoException;
import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.domain.model.Pedido;
import com.proyectos.DeliveryApp.domain.model.Usuario;
import com.proyectos.DeliveryApp.domain.ports.out.PedidoRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.ports.out.RestauranteRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.ports.out.UsuarioRepositoryOutPorts;
import com.proyectos.DeliveryApp.infraestructure.entity.PedidoEntity;
import com.proyectos.DeliveryApp.infraestructure.entity.UsuarioEntity;
import com.proyectos.DeliveryApp.infraestructure.repository.PedidoRepositoryJpa;
import com.proyectos.DeliveryApp.infraestructure.repository.RestauranteRepositoryJpa;
import com.proyectos.DeliveryApp.infraestructure.repository.UsuarioRepositoryJpa;
import com.proyectos.DeliveryApp.domain.enums.Rol;
import com.proyectos.DeliveryApp.domain.ports.in.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

   private final PedidoRepositoryOutPorts repository;
   private final UsuarioRepositoryOutPorts usuarioRepository;
   private final RestauranteRepositoryOutPorts restauranteRepository;


    @Override
    public List<Pedido> listar() {
        return repository.findAll();
    }


    @Override
    public Pedido crear(Pedido pedido) {

        Usuario cliente = usuarioRepository.findById(pedido.getClienteId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(pedido.getClienteId()));

        pedido.setClienteId(cliente.getId());

        pedido.setFecha(LocalDateTime.now());
        pedido.setEstadoPedido(EstadoPedido.ACEPTADO);

        return repository.save(pedido);
    }


    @Override
    public Pedido cancelar(Long id) {
        if (id == null){
            throw  new IllegalStateException("El ID es obligatorio");
        }

        Pedido pedido1 = repository.findById(id).
                orElseThrow(() -> new PedidoNoEncontradoException(id));

        pedido1.setEstadoPedido(EstadoPedido.CANCELADO);
        return repository.save(pedido1);
    }

    @Override
    public Pedido cambiarEstado(Long id, EstadoPedido estado) {
        if (id == null){
            throw  new IllegalStateException("El ID es obligatorio");
        }
        Pedido pedido = repository.findById(id).
                orElseThrow(() -> new PedidoNoEncontradoException(id));

        if(pedido.getEstadoPedido() == EstadoPedido.ENTREGADO ||
                pedido.getEstadoPedido() == EstadoPedido.CANCELADO){
            throw  new IllegalStateException("El estado no se puede modificar una vez este finalizado");
        }

        pedido.setEstadoPedido(estado);
        return repository.save(pedido);
    }

    @Override
    public Pedido asignarRepartidor(Long pedidoId, Long repartidorId) {
        if (pedidoId == null) {
            throw new IllegalArgumentException("El ID del pedido es obligatorio");
        }
        if (repartidorId == null) {
            throw new IllegalArgumentException("El ID del repartidor es obligatorio");
        }

        // Buscar pedido
        Pedido pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));

        // Validar estado del pedido
        if (pedido.getEstadoPedido() == EstadoPedido.CANCELADO ||
                pedido.getEstadoPedido() == EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("No se puede asignar repartidor a un pedido finalizado");
        }

        if (pedido.getRepartidorId() != null) {
            throw new IllegalStateException("El pedido ya tiene repartidor asignado");
        }

        Usuario repartidor = usuarioRepository.findById(repartidorId)
                .orElseThrow(() -> new UsuarioNoEncontradoException(repartidorId));

        if (repartidor.getRol() != Rol.REPARTIDOR) {
            throw new IllegalStateException("El usuario no es repartidor");
        }
        pedido.setRepartidorId(repartidor.getId());
        pedido.setEstadoPedido(EstadoPedido.EN_CAMINO);

        return repository.save(pedido);
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(Long clienteId) {
        if(clienteId == null){
            throw new IllegalArgumentException("El ID del cliente es obligatorio");
        }

        if (!usuarioRepository.existsById(clienteId)){
            throw new UsuarioNoEncontradoException(clienteId);
        }

        return repository.findByClienteId(clienteId);
    }

    @Override
    public List<Pedido> obtenerPedidosPorRestaurante(Long restauranteId) {

        if (restauranteId == null){
            throw new IllegalArgumentException("El ID del restaurante es obligatorio");
        }

        if (!restauranteRepository.existsById(restauranteId)){
            throw new RestauranteNoEncontradoException(restauranteId);
        }

        return repository.findByRestauranteId(restauranteId);
    }

    @Override
    public List<Pedido> obtenerPorEstado(EstadoPedido estado) {

        if (estado == null){
        throw new IllegalArgumentException("El ESTADO del pedido es obligatorio");
        }

        return repository.findByEstadoPedido(estado);
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException(id));
    }

}
