package com.banquito.fullpay.order.controller;

import com.banquito.fullpay.order.model.ItemOrden;
import com.banquito.fullpay.order.model.Orden;
import com.banquito.fullpay.order.service.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {
    private final OrdenService service;

    public OrdenController(OrdenService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Orden>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> getById(@PathVariable Long id) {
        Orden orden = service.findById(id);
        return orden != null ? ResponseEntity.ok(orden) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Orden> create(@RequestBody Orden orden) {
        return ResponseEntity.ok(service.createOrden(orden));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orden> update(@PathVariable Long id, @RequestBody Orden orden) {
        if (service.findById(id) != null) {
            return ResponseEntity.ok(service.createOrden(orden));
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

    @PostMapping("/upload")
    public ResponseEntity<Orden> uploadOrderWithItems(@RequestParam("file") MultipartFile file) {
        // Aquí iría la lógica para procesar el archivo y convertirlo en una Orden y List<ItemOrden>
        Orden orden = new Orden();  // Crear la orden a partir del archivo
        List<ItemOrden> items = Stream.of(new ItemOrden()).collect(Collectors.toList());  // Crear los items a partir del archivo

        return ResponseEntity.ok(service.saveOrderWithItems(orden, items));
    }

    @PutMapping("/{orderId}/associateCobro/{codCobro}")
    public ResponseEntity<Orden> associateCobro(@PathVariable Long orderId, @PathVariable Long codCobro) {
        try {
            return ResponseEntity.ok(service.associateCobro(orderId, codCobro));
        } catch (RuntimeException rte) {
            return ResponseEntity.badRequest().build();
        }
    }
}
