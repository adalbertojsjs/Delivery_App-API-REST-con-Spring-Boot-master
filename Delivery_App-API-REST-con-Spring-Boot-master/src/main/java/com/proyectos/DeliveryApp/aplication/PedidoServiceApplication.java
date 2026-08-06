package com.proyectos.DeliveryApp.aplication;

import com.proyectos.DeliveryApp.domain.Exception.PedidoNoEncontradoException;
import com.proyectos.DeliveryApp.domain.Exception.UsuarioNoEncontradoException;
import com.proyectos.DeliveryApp.domain.enums.EstadoPedido;
import com.proyectos.DeliveryApp.domain.enums.Rol;
import com.proyectos.DeliveryApp.domain.model.Pedido;
import com.proyectos.DeliveryApp.domain.ports.in.PedidoService;
import com.proyectos.DeliveryApp.domain.ports.out.PedidoRepositoryOutPorts;
import com.proyectos.DeliveryApp.domain.ports.out.UsuarioRepositoryOutPorts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PedidoServiceApplication implements PedidoService {

   private final PedidoRepositoryOutPorts repository;
   private final UsuarioRepositoryOutPorts usuarioRepository;

    @Override
    public List<Pedido> listar() {
        return repository.findAll();
    }


    @Override
    public Pedido crear(Pedido pedido) {

        if (pedido == null){
            throw  new IllegalArgumentException("El pedido no puede ser nulo");
        }

        if (pedido.getClienteId() == null){
            throw  new IllegalArgumentException("El id del cliente no puede ser nulo");

        }
        if (pedido.getRestauranteId() == null){
            throw  new IllegalArgumentException("El id del restaurante no puede ser nulo");

        }

        var cliente = usuarioRepository.findById(pedido.getClienteId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(pedido.getClienteId()));

        pedido.setClienteId(cliente.getId());

        pedido.setFecha(LocalDateTime.now());
        pedido.setEstadoPedido(EstadoPedido.ACEPTADO);

        return repository.save(pedido);
    }


    @Override
    public Pedido cancelar(Long id) {
        var pedido1 = repository.findById(id).
                orElseThrow(() -> new PedidoNoEncontradoException(id));

        pedido1.setEstadoPedido(EstadoPedido.CANCELADO);
        return repository.save(pedido1);
    }

    @Override
    public Pedido cambiarEstado(Long id, EstadoPedido estado) {

        var pedido = repository.findById(id).
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

        var pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));

        if (pedido.getEstadoPedido() == EstadoPedido.CANCELADO ||
                pedido.getEstadoPedido() == EstadoPedido.ENTREGADO) {
            throw new IllegalArgumentException("No se puede asignar repartidor a un pedido finalizado");
        }

        if (pedido.getRepartidorId() != null) {
            throw new IllegalArgumentException("El pedido ya tiene repartidor asignado");
        }

        var repartidor = usuarioRepository.findById(repartidorId)
                .orElseThrow(() -> new UsuarioNoEncontradoException(repartidorId));

        if (repartidor.getRol() != Rol.REPARTIDOR) {
            throw new IllegalArgumentException("El usuario no es repartidor");
        }
        pedido.setRepartidorId(repartidor.getId());
        pedido.setEstadoPedido(EstadoPedido.EN_CAMINO);

        return repository.save(pedido);
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(Long clienteId) {

        return repository.findByClienteId(clienteId);
    }

    @Override
    public List<Pedido> obtenerPedidosPorRestaurante(Long restauranteId) {

        return repository.findByRestauranteId(restauranteId);
    }

    @Override
    public List<Pedido> obtenerPorEstado(EstadoPedido estado) {

        return repository.findByEstadoPedido(estado);
    }

    @Override
    public Pedido buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException(id));
    }
}