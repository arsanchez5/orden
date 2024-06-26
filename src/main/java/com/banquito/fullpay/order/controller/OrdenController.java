package com.banquito.fullpay.order.controller;

import com.banquito.fullpay.order.service.OrdenService;
import com.banquito.fullpay.order.dto.OrdenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {
    private final OrdenService service;

    public OrdenController(OrdenService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrdenDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenDTO> getById(@PathVariable Long id) {
        OrdenDTO orden = service.findById(id);
        return orden != null ? ResponseEntity.ok(orden) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrdenDTO> create(@RequestBody OrdenDTO ordenDTO) {
        return ResponseEntity.ok(service.createOrden(ordenDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenDTO> update(@PathVariable Long id, @RequestBody OrdenDTO ordenDTO) {
        if (service.findById(id) != null) {
            return ResponseEntity.ok(service.createOrden(ordenDTO));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id) != null) {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{orderId}/associateCobro/{codCobro}")
    public ResponseEntity<OrdenDTO> associateCobro(@PathVariable Long orderId, @PathVariable Long codCobro) {
        try {
            return ResponseEntity.ok(service.associateCobro(orderId, codCobro));
        } catch (RuntimeException rte) {
            return ResponseEntity.badRequest().build();
        }
    }
}