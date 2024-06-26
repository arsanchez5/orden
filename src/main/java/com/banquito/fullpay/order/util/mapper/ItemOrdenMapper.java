package com.banquito.fullpay.order.util.mapper;

import org.mapstruct.Mapper;

import com.banquito.fullpay.order.dto.ItemOrdenDTO;
import com.banquito.fullpay.order.model.ItemOrden;

@Mapper
public interface ItemOrdenMapper {

    ItemOrdenDTO toDTO(ItemOrden itemOrden);
    ItemOrden toEntity(ItemOrdenDTO itemOrdenDTO);
}
