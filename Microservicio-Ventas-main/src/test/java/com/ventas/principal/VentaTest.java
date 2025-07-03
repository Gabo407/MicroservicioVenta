package com.ventas.principal;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.ventas.principal.Model.Ventas;
import com.ventas.principal.Model.dto.VentasDto;
import com.ventas.principal.Model.entity.VentasEntity;
import com.ventas.principal.Repository.VentasRepository;
import com.ventas.principal.Service.VentasService;

public class VentaTest {

    @Mock
    private VentasRepository ventasRepository;

    @InjectMocks
    private VentasService ventasService;

    private Ventas venta;
    private VentasEntity ventaEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        venta = new Ventas(1, 5, 1000, "2024-06-23", "Efectivo");
        ventaEntity = new VentasEntity();
        ventaEntity.setIdVenta(1);
        ventaEntity.setCantProducto(5);
        ventaEntity.setTotalVenta(1000);
        ventaEntity.setFechaPago("2024-06-23");
        ventaEntity.setMetodoPago("Efectivo");
    }

    @Test
    public void testCrearVenta() {
        when(ventasRepository.save(any(VentasEntity.class))).thenReturn(ventaEntity);
        String result = ventasService.crearVenta(venta);
        assertEquals("Venta creada exitosamente", result);
    }

    @Test
    public void testObtenerVentaExiste() {
        when(ventasRepository.findById(anyInt())).thenReturn(Optional.of(ventaEntity));
        Ventas result = ventasService.obtenerVenta(1);
        assertNotNull(result);
        assertEquals(venta.getIdVenta(), result.getIdVenta());
    }

    @Test
    public void testObtenerVentaNoExiste() {
        when(ventasRepository.findById(anyInt())).thenReturn(Optional.empty());
        Ventas result = ventasService.obtenerVenta(2);
        assertNull(result);
    }

    @Test
    public void testObtenerTodasLasVentas() {
        when(ventasRepository.findAll()).thenReturn(Arrays.asList(ventaEntity));
        List<Ventas> result = ventasService.obtenerTodasLasVentas();
        assertEquals(1, result.size());
        assertEquals(venta.getIdVenta(), result.get(0).getIdVenta());
    }

    @Test
    public void testActualizarVentaExiste() {
        when(ventasRepository.findById(anyInt())).thenReturn(Optional.of(ventaEntity));
        when(ventasRepository.save(any(VentasEntity.class))).thenReturn(ventaEntity);
        String result = ventasService.actualizarVenta(1, venta);
        assertEquals("Venta actualizada exitosamente", result); 
    }

    @Test
    public void testActualizarVentaNoExiste() {
        when(ventasRepository.findById(anyInt())).thenReturn(Optional.empty());
        String result = ventasService.actualizarVenta(2, venta);
        assertEquals("Venta no encontrada", result);
    }

    @Test
    public void testEliminarVentaExiste() {
        when(ventasRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(ventasRepository).deleteById(anyInt());
        String result = ventasService.eliminarVenta(1);
        assertEquals("Venta eliminada correctamente", result);
    }

    @Test
    public void testEliminarVentaNoExiste() {
        when(ventasRepository.existsById(anyInt())).thenReturn(false);
        String result = ventasService.eliminarVenta(2);
        assertEquals("Venta no encontrada", result);
    }

    @Test
    public void testObtenerVentaPorIdExiste() {
        when(ventasRepository.findById(anyInt())).thenReturn(Optional.of(ventaEntity));
        VentasDto dto = ventasService.obtenerVentaPorId(1);
        assertNotNull(dto);
        assertEquals(venta.getCantProducto(), dto.getCantProducto());
    }

    @Test
    public void testObtenerVentaPorIdNoExiste() {
        when(ventasRepository.findById(anyInt())).thenReturn(Optional.empty());
        VentasDto dto = ventasService.obtenerVentaPorId(2);
        assertNull(dto);
    }
}
