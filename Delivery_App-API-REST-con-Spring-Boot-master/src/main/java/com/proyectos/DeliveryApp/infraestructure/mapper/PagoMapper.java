package com.proyectos.DeliveryApp.infraestructure.mapper;


import com.proyectos.DeliveryApp.domain.model.Pago;
import com.proyectos.DeliveryApp.infraestructure.entity.PagoEntity;
import com.proyectos.DeliveryApp.infraestructure.http.dto.request.PagoRequest;
import com.proyectos.DeliveryApp.infraestructure.http.dto.response.PagoResponse;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {


        public  PagoEntity toEntity(Pago pago) {
            if (pago == null) {
                return null;
            }

            return PagoEntity.builder()
                    .id(pago.getId())
                    .pedidoId(pago.getPedidoId())
                    .nombreComprador(pago.getNombreComprador())
                    .numeroTarjeta(pago.getNumeroTarjeta())
                    .fecha(pago.getFecha())
                    .costoFinal(pago.getCostoFinal())
                    .build();
        }

        public  Pago toDomain(PagoEntity entity) {
            if (entity == null) {
                return null;
            }

            return Pago.builder()
                    .id(entity.getId())
                    .pedidoId(entity.getPedidoId())
                    .nombreComprador(entity.getNombreComprador())
                    .numeroTarjeta(entity.getNumeroTarjeta())
                    .fecha(entity.getFecha())
                    .costoFinal(entity.getCostoFinal())
                    .build();
        }


    public PagoResponse toResponse(Pago pago) {
        if (pago == null) {
            return null;
        }

        return PagoResponse.builder()
                .id(pago.getId())
                .pedidoId(pago.getPedidoId())
                .nombreComprador(pago.getNombreComprador())
                .numeroTarjeta(pago.getNumeroTarjeta())
                .fecha(pago.getFecha())
                .costoFinal(pago.getCostoFinal())
                .build();
    }

    public  Pago requestToDomain(PagoRequest request) {
        if (request == null) {
            return null;
        }

        return Pago.builder()
                .nombreComprador(request.getNombreComprador())
                .numeroTarjeta(request.getNumeroTarjeta())
                .build();
    }

}
