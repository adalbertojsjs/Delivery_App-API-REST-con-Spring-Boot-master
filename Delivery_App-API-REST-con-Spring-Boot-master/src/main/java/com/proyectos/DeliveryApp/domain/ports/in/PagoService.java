package com.proyectos.DeliveryApp.domain.ports.in;

import com.proyectos.DeliveryApp.domain.model.Pago;

public interface PagoService {

    Pago Pagar(Long pedidoid, Pago pago);

    Pago buscarPagoId(Long id);


}