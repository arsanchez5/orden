package com.banquito.fullpay.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.banquito.fullpay.order.model.Orden;
import com.banquito.fullpay.order.repository.OrdenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdenService {

    private final OrdenRepository ordenRepository;

    public OrdenService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
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

    @Transactional
    public void saveAll(List<Orden> ordenes) {
        ordenRepository.saveAll(ordenes);
    }


}
