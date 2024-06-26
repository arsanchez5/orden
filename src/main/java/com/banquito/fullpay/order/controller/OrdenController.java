package com.banquito.fullpay.order.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.banquito.fullpay.order.dto.OrdenDTO;
import com.banquito.fullpay.order.service.OrdenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrdenDTO> crearOrden(@RequestBody OrdenDTO ordenDTO) {
        OrdenDTO createdOrden = ordenService.crearOrden(ordenDTO);
        return ResponseEntity.ok(createdOrden);
    }

    @GetMapping
    public ResponseEntity<List<OrdenDTO>> findAll() {
        List<OrdenDTO> ordenes = ordenService.getAllOrdenes();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenDTO> findById(@PathVariable Long id) {
        OrdenDTO ordenDTO = ordenService.obtenerOrdenPorId(id);
        if (ordenDTO != null) {
            return ResponseEntity.ok(ordenDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        ordenService.eliminarOrden(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<OrdenDTO> uploadOrderWithItems(@RequestParam("file") MultipartFile file) {
        try {
            String fileType = file.getContentType();
            OrdenDTO ordenDTO = null;

            if (fileType != null) {
                if (fileType.equals("application/json")) {
                    ordenDTO = parseJson(file);
                } else if (fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    ordenDTO = parseExcel(file);
                } else if (fileType.equals("text/csv") || fileType.equals("application/csv")) {
                    ordenDTO = parseCsv(file);
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }

            OrdenDTO createdOrden = ordenService.saveOrderWithItems(ordenDTO);
            return ResponseEntity.ok(createdOrden);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private OrdenDTO parseJson(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file.getInputStream(), OrdenDTO.class);
    }

    private OrdenDTO parseExcel(MultipartFile file) throws IOException {
        List<OrdenDTO> ordenList = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            if (rows.hasNext())
                rows.next();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                OrdenDTO ordenDTO = OrdenDTO.builder()
                        .id((long) currentRow.getCell(0).getNumericCellValue())
                        .codCobro((long) currentRow.getCell(1).getNumericCellValue())
                        .fechaInicio(currentRow.getCell(2).getLocalDateTimeCellValue())
                        .fechaFin(currentRow.getCell(3).getLocalDateTimeCellValue())
                        .montoTotal(new BigDecimal(currentRow.getCell(4).getNumericCellValue()))
                        .build();
                ordenList.add(ordenDTO);
            }
        }
        // Assuming single order per file for simplicity
        return ordenList.isEmpty() ? null : ordenList.get(0);
    }

    private OrdenDTO parseCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<OrdenDTO> csvToBean = new CsvToBeanBuilder<OrdenDTO>(reader)
                    .withType(OrdenDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<OrdenDTO> ordenList = csvToBean.parse();
            return ordenList.isEmpty() ? null : ordenList.get(0);
        }
    }

}
