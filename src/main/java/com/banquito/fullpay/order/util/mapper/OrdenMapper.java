package com.banquito.fullpay.order.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.banquito.fullpay.order.dto.OrdenDTO;
import com.banquito.fullpay.order.model.Orden;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { ItemOrdenMapper.class, CobroMapper.class })
public interface OrdenMapper {

    OrdenMapper INSTANCE = Mappers.getMapper(OrdenMapper.class);

    @Mapping(source = "codCobro", target = "codCobro")
    @Mapping(source = "items", target = "items")
    OrdenDTO toDTO(Orden orden);

    @Mapping(source = "codCobro", target = "codCobro")
    @Mapping(source = "items", target = "items")
    Orden toEntity(OrdenDTO ordenDTO);
}
