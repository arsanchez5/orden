package com.banquito.fullpay.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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

    public Orden createOrden(Orden orden) {
        return ordenRepository.save(orden);
    }

    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    @Transactional(Transactional.TxType.NEVER)
    public Orden findById(Long id) {
        Optional<Orden> ordenOpt = this.ordenRepository.findById(id);
        if (ordenOpt.isPresent()) {
            return ordenOpt.get();
        } else {
            throw new RuntimeException("No existe la orden con id: " + id);
        }
    }

    public void deleteById(Long id) {
        ordenRepository.deleteById(id);
    }

    @Transactional
    public Orden saveOrderWithItems(Orden orden, List<ItemOrden> items) {
        Orden savedOrder = ordenRepository.save(orden);

        for (ItemOrden item : items) {
            item.setOrden(savedOrder);
            itemOrdenRepository.save(item);
        }

        return savedOrder;
    }

    @Transactional
    public Orden associateCobro(Long orderId, Long codCobro) {
        Orden orden = ordenRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Orden not found"));

        orden.setCodCobro(codCobro);

        return ordenRepository.save(orden);
    }
}
