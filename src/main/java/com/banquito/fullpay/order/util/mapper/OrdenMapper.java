package com.banquito.fullpay.order.util.mapper;

import org.mapstruct.Mapper;

import com.banquito.fullpay.order.dto.OrdenDTO;
import com.banquito.fullpay.order.model.Orden;

@Mapper
public interface OrdenMapper {

    OrdenDTO toDTO(Orden orden);

    Orden toEntity(OrdenDTO ordenDTO);
}
