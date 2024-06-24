package com.banquito.fullpay.order.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.banquito.fullpay.order.model.Orden;
import com.banquito.fullpay.order.service.OrdenService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @PostMapping("/create")
    public ResponseEntity<String> createOrdenes(@RequestBody List<Orden> ordenes) {
        try {
            ordenService.saveAll(ordenes);
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing data: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
        }

        try {
            List<Orden> ordenes = parseFile(file);
            ordenService.saveAll(ordenes);
            return ResponseEntity.ok("File uploaded and data saved successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }

    private List<Orden> parseFile(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file.getInputStream(), new TypeReference<List<Orden>>() {});
    }
}
