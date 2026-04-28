package com.proyectos.DeliveryApp.domain.Exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class GlobalExceptionHandler {

@ExceptionHandler({PedidoNoEncontradoException.class,
                   RestauranteNoEncontradoException.class,
                    UsuarioNoEncontradoException.class,ProductoNoEncontrado.class})
public  ResponseEntity<ErrorResponse> manejarNoFound(RuntimeException ex , HttpServletRequest request){


    ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value()
            ,"Recurso no encontrado",
            ex.getMessage()
            ,request.getRequestURI() );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}

}
