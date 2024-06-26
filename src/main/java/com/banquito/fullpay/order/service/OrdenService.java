package com.banquito.fullpay.order.service;

import org.springframework.stereotype.Service;

import com.banquito.fullpay.order.dto.OrdenDTO;
import com.banquito.fullpay.order.model.ItemOrden;
import com.banquito.fullpay.order.model.Orden;
import com.banquito.fullpay.order.repository.ItemOrdenRepository;
import com.banquito.fullpay.order.repository.OrdenRepository;
import com.banquito.fullpay.order.util.mapper.OrdenMapper;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ItemOrdenRepository itemOrdenRepository;

    private final OrdenMapper ordenMapper;

   

    public OrdenService(OrdenRepository ordenRepository, ItemOrdenRepository itemOrdenRepository,
            OrdenMapper ordenMapper) {
        this.ordenRepository = ordenRepository;
        this.itemOrdenRepository = itemOrdenRepository;
        this.ordenMapper = ordenMapper;
    }

    public OrdenDTO obtenerOrdenPorId(Long id) {
        Optional<Orden> ordenOpt = ordenRepository.findById(id);
        if (ordenOpt.isPresent()) {
            return ordenMapper.toDTO(ordenOpt.get());
        } else {
            throw new RuntimeException("Orden no encontrada con id: " + id);
        }
    }

    public List<OrdenDTO> getAllOrdenes() {
        try {
            return ordenRepository.findAll().stream().map(ordenMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las ordenes");
        }
    }

    public OrdenDTO crearOrden(OrdenDTO ordenDTO) {
        Orden orden = ordenMapper.toEntity(ordenDTO);
        Orden nuevaOrden = ordenRepository.save(orden);
        return ordenMapper.toDTO(nuevaOrden);
    }

    public List<OrdenDTO> obtenerTodasLasOrdenes() {
        List<Orden> ordenes = ordenRepository.findAll();
        return ordenes.stream()
                .map(ordenMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void eliminarOrden(Long id) {
        ordenRepository.deleteById(id);
    }

    public OrdenDTO actualizarOrden(Long id, OrdenDTO ordenDTO) {
        if (!ordenRepository.existsById(id)) {
            throw new RuntimeException("Orden no encontrada con id: " + id);
        }
        Orden orden = ordenMapper.toEntity(ordenDTO);
        orden.setId(id);
        Orden ordenActualizada = ordenRepository.save(orden);
        return ordenMapper.toDTO(ordenActualizada);
    }

    @Transactional
    public OrdenDTO saveOrderWithItems(OrdenDTO ordenDTO) {
        Orden orden = ordenMapper.toEntity(ordenDTO);
        Orden savedOrden = ordenRepository.save(orden);

        for (ItemOrden item : savedOrden.getItems()) {
            item.setOrden(savedOrden);
            itemOrdenRepository.save(item);
        }

        return ordenMapper.toDTO(savedOrden);
    }

}
