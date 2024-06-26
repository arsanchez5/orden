package com.banquito.fullpay.order.util.mapper;

import org.mapstruct.Mapper;

import com.banquito.fullpay.order.dto.ItemOrdenDTO;
import com.banquito.fullpay.order.model.ItemOrden;

@Mapper
public interface ItemOrdenMapper {
    ItemOrdenMapper INSTANCE = Mappers.getMapper(ItemOrdenMapper.class);

    @Mapping(target = "orden", ignore = true)
    ItemOrdenDTO toDTO(ItemOrden itemOrden);

    @Mapping(target = "orden", ignore = true)
    ItemOrden toEntity(ItemOrdenDTO itemOrdenDTO);
}