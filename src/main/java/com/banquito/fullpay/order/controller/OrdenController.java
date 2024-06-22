package com.banquito.fullpay.order.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banquito.fullpay.order.model.Orden;
import com.banquito.fullpay.order.service.OrdenService;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> getOrdenById(@PathVariable Long id) {
        try {
            Orden orden = ordenService.findById(id);
            return ResponseEntity.ok(orden);
        } catch (RuntimeException rte) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Orden>> getAllOrdenes() {
        List<Orden> ordenes = ordenService.findAll();
        return ResponseEntity.ok(ordenes);
    }

    @PostMapping
    public ResponseEntity<Orden> createOrden(@RequestBody Orden orden) {
        Orden createdOrden = ordenService.createOrden(orden);
        return ResponseEntity.ok(createdOrden);
    }
}
