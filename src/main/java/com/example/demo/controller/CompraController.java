package com.example.demo.controller;

import com.example.demo.entity.Compra;
import com.example.demo.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    @Autowired
    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public ResponseEntity<List<Compra>> obtenerTodosLasCompras() {
        return new ResponseEntity<>(compraService.obtenerTodosLasCompras(), HttpStatus.OK);
    }

    @GetMapping("/ordenadas")
    public ResponseEntity<List<Compra>> obtenerComprasOrdenadasDesc() {
        List<Compra> compras = compraService.obtenerComprasOrdenadasDescPorId();
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerCompraPorId(@PathVariable Long id) {
        Compra compra = compraService.obtenerCompraPorId(id);
        if (compra != null) {
            return new ResponseEntity<>(compra, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nitci/{nitCi}")
    public ResponseEntity<Compra> obtenerProveedorPorFecha(@PathVariable LocalDate fecha) {
        Compra compra = compraService.obtenerProveedorPorFecha(fecha);
        if (compra != null) {
            return new ResponseEntity<>(compra, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearCompra(@RequestBody Compra compra) {
        try {
            Compra nuevoCompra = compraService.crearCompra(compra);
            return new ResponseEntity<>(nuevoCompra, HttpStatus.CREATED);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            return new ResponseEntity<>("Ya existe una compra con ese ID.", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error interno al guardar compra.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoCompra(
            @PathVariable Long id,
            @RequestBody Map<String, String> estadoRequest) {

        try {
            String nuevoEstadoStr = estadoRequest.get("estado");
            Compra.EstadoCompra nuevoEstado = Compra.EstadoCompra.valueOf(nuevoEstadoStr.toUpperCase());

            Compra compraActualizada = compraService.cambiarEstadoCompra(id, nuevoEstado);

            if (compraActualizada != null) {
                return new ResponseEntity<>(compraActualizada, HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado no válido. Los estados permitidos son: PENDIENTE, APROBADO, CANCELADA");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al cambiar el estado de la compra");
        }
    }

    /*@GetMapping("/filtradas")
    public ResponseEntity<List<Compra>> getComprasFiltradas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) String tipoProveedor,
            @RequestParam(required = false) String estadoCompra,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Compra> compras = compraService.filtrarCompras(
                fechaInicio,
                fechaFin,
                tipoProveedor,
                estadoCompra,
                pageable
        );

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(compras.getTotalElements()))
                .body(compras.getContent());
    }*/

    @GetMapping("/filtradas")
    public ResponseEntity<Page<Compra>> getComprasFiltradas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) String tipoProveedor,
            @RequestParam(required = false) String estadoCompra,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Compra> compras = compraService.filtrarCompras(
                fechaInicio,
                fechaFin,
                tipoProveedor,
                estadoCompra,
                pageable
        );

        return ResponseEntity.ok(compras);
    }

}
