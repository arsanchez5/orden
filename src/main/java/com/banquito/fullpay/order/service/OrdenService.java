package com.banquito.fullpay.order.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.fullpay.order.dto.OrdenDTO;
import com.banquito.fullpay.order.util.mapper.OrdenMapper;
import com.banquito.fullpay.order.model.ItemOrden;
import com.banquito.fullpay.order.model.Orden;
import com.banquito.fullpay.order.repository.ItemOrdenRepository;
import com.banquito.fullpay.order.repository.OrdenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ItemOrdenRepository itemOrdenRepository;

    public OrdenService(OrdenRepository ordenRepository, ItemOrdenRepository itemOrdenRepository) {
        this.ordenRepository = ordenRepository;
        this.itemOrdenRepository = itemOrdenRepository;
    }

    public OrdenDTO createOrden(OrdenDTO ordenDTO) {
        Orden orden = OrdenMapper.INSTANCE.toEntity(ordenDTO);
        Orden savedOrden = ordenRepository.save(orden);
        return OrdenMapper.INSTANCE.toDTO(savedOrden);
    }

    public List<OrdenDTO> findAll() {
        return ordenRepository.findAll().stream()
                .map(OrdenMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(Transactional.TxType.NEVER)
    public OrdenDTO findById(Long id) {
        Optional<Orden> ordenOpt = this.ordenRepository.findById(id);
        return ordenOpt.map(OrdenMapper.INSTANCE::toDTO).orElse(null);
    }

    public void deleteById(Long id) {
        ordenRepository.deleteById(id);
    }

    @Transactional
    public OrdenDTO saveOrderWithItems(OrdenDTO ordenDTO) {
        Orden orden = OrdenMapper.INSTANCE.toEntity(ordenDTO);
        Orden savedOrden = ordenRepository.save(orden);

        for (ItemOrden item : savedOrden.getItems()) {
            item.setOrden(savedOrden);
            itemOrdenRepository.save(item);
        }

        return OrdenMapper.INSTANCE.toDTO(savedOrden);
    }

    @Transactional
    public OrdenDTO associateCobro(Long orderId, Long codCobro) {
        Orden orden = ordenRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Orden not found"));

        orden.setCodCobro(codCobro);

        Orden updatedOrden = ordenRepository.save(orden);

        return OrdenMapper.INSTANCE.toDTO(updatedOrden);
    }
}
