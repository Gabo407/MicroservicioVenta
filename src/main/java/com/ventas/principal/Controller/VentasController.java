package com.ventas.principal.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventas.principal.Model.Ventas;
import com.ventas.principal.Model.dto.VentasDto;
import com.ventas.principal.Service.VentasService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/ventas")
public class VentasController {
    @Autowired
    private VentasService ventasService;

    @PostMapping
    @Operation(summary = "Este endpoint permite AGREGAR ventas")
    public ResponseEntity<String> crearVenta(@Valid @RequestBody VentasDto ventaDto) {
        Ventas venta = new Ventas(
            null,
            ventaDto.getCantProducto(),
            ventaDto.getTotalVenta(),
            ventaDto.getFechaPago(),
            ventaDto.getMetodoPago()
        );
        ventasService.crearVenta(venta);
        return ResponseEntity.ok("Venta creada exitosamente");
    }

    @GetMapping("/{idVenta}")
    @Operation(summary = "Este endpoint permite OBTENER una venta")
    public ResponseEntity<VentasDto> obtenerVenta(@PathVariable Integer idVenta) {
        VentasDto venta = ventasService.obtenerVentaPorId(idVenta);
        if (venta != null) {
            return ResponseEntity.ok(venta);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Este endpoint permite OBTENER todas las ventas")
    public ResponseEntity<List<VentasDto>> obtenerTodasLasVentas() {
        List<VentasDto> ventas = ventasService.obtenerTodasLasVentas().stream()
            .map(v -> new VentasDto(
                v.getIdVenta(),
                v.getCantProducto(),
                v.getTotalVenta(),
                v.getFechaPago(),
                v.getMetodoPago()
            )).collect(Collectors.toList());
        return ResponseEntity.ok(ventas);
    }

    @PutMapping("/{idVenta}")
    @Operation(summary = "Este endpoint permite ACTUALIZA una venta")
    public ResponseEntity<?> actualizarVenta(@PathVariable Integer idVenta, @Valid @RequestBody VentasDto ventaDto) {
        try {
            Ventas venta = new Ventas(
                idVenta,
                ventaDto.getCantProducto(),
                ventaDto.getTotalVenta(),
                ventaDto.getFechaPago(),
                ventaDto.getMetodoPago()
            );
            String resultado = ventasService.actualizarVenta(idVenta, venta);

            if (resultado.equals("Venta no encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Venta no encontrada"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Venta actualizada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("mensaje", "Error al actualizar la venta: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{idVenta}")
    @Operation(summary = "Este endpoint permite ELIMINA una venta")
    public ResponseEntity<String> eliminarVenta(@PathVariable Integer idVenta) {
        String mensaje = ventasService.eliminarVenta(idVenta);
        if ("Venta eliminada correctamente".equals(mensaje)) {
            return ResponseEntity.ok(mensaje);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/dto/{idVenta}")
    @Operation(summary = "Este endpoint permite OBTENER una venta con parametros definidos en el DTO")
    public ResponseEntity<VentasDto> obtenerVentaDto(@PathVariable Integer idVenta) {
        VentasDto ventaDto = ventasService.obtenerVentaPorId(idVenta);
        if (ventaDto != null) {
            return ResponseEntity.ok(ventaDto);
        }
        return ResponseEntity.notFound().build();
    }
}