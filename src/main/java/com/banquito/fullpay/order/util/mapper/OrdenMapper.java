package com.banquito.fullpay.order.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.banquito.fullpay.order.dto.OrdenDTO;
import com.banquito.fullpay.order.model.Orden;

@Mapper
public interface OrdenMapper {
    OrdenMapper INSTANCE = Mappers.getMapper(OrdenMapper.class);

    OrdenDTO toDTO(Orden orden);

    Orden toEntity(OrdenDTO ordenDTO);
}
