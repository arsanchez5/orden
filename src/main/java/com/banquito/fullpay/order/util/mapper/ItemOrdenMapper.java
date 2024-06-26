package com.banquito.fullpay.order.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.fullpay.order.dto.ItemOrdenDTO;
import com.banquito.fullpay.order.model.ItemOrden;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemOrdenMapper {

    ItemOrdenDTO toDTO(ItemOrden itemOrden);
    ItemOrden toEntity(ItemOrdenDTO itemOrdenDTO);
}
