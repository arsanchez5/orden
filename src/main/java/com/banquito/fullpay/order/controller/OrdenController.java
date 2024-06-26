package com.banquito.fullpay.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.fullpay.order.dto.OrdenDTO;
import com.banquito.fullpay.order.service.OrdenService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenDTO> obtenerOrdenPorId(@PathVariable Long id) {
        try {
            OrdenDTO ordenDTO = ordenService.obtenerOrdenPorId(id);
            return ResponseEntity.ok(ordenDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrdenDTO> crearOrden(@RequestBody OrdenDTO ordenDTO) {
        OrdenDTO nuevaOrden = ordenService.crearOrden(ordenDTO);
        return ResponseEntity.ok(nuevaOrden);
    }

    @GetMapping
    public ResponseEntity<List<OrdenDTO>> obtenerTodasLasOrdenes() {
        List<OrdenDTO> ordenes = ordenService.obtenerTodasLasOrdenes();
        return ResponseEntity.ok(ordenes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        try {
            ordenService.eliminarOrden(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenDTO> actualizarOrden(@PathVariable Long id, @RequestBody OrdenDTO ordenDTO) {
        try {
            OrdenDTO ordenActualizada = ordenService.actualizarOrden(id, ordenDTO);
            return ResponseEntity.ok(ordenActualizada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
