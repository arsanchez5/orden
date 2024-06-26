package com.banquito.fullpay.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrdenDTO {

    private Long id;
    private Long codCobro;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private BigDecimal montoTotal;
    private List<ItemOrdenDTO> items;
   
}
