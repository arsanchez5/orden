package com.banquito.fullpay.order.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemOrdenDTO {

    Long id;
    String numeroContrapartida;
    String cedula;
    String nombres;
    String apellidos;
    BigDecimal monto;
}
