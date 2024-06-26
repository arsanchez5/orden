package com.banquito.fullpay.order.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.banquito.fullpay.order.model.Cobro;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CobroMapper {

    CobroMapper INSTANCE = Mappers.getMapper(CobroMapper.class);

    CobroDTO toDTO(Cobro cobro);
    Cobro toEntity(CobroDTO cobroDTO);

}
