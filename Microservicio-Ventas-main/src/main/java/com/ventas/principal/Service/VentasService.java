package com.ventas.principal.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ventas.principal.Model.Ventas;
import com.ventas.principal.Model.dto.VentasDto;
import com.ventas.principal.Model.entity.VentasEntity;
import com.ventas.principal.Repository.VentasRepository;

import jakarta.transaction.Transactional;

@Service
public class VentasService {

    @Autowired
    private VentasRepository ventasRepository;

    @Transactional
    public String crearVenta(Ventas venta) {
        VentasEntity ventaNueva = mapToEntity(venta);
        ventasRepository.save(ventaNueva);
        return "Venta creada exitosamente";
    }

    public Ventas obtenerVenta(Integer idVenta) {
        return ventasRepository.findById(idVenta)
                .map(this::mapToModel)
                .orElse(null);
    }

    public List<Ventas> obtenerTodasLasVentas() {
        return ventasRepository.findAll().stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public String actualizarVenta(Integer idVenta, Ventas venta) {
        return ventasRepository.findById(idVenta).map(existente -> {
            existente.setCantProducto(venta.getCantProducto());
            existente.setTotalVenta(venta.getTotalVenta());
            existente.setFechaPago(venta.getFechaPago());
            existente.setMetodoPago(venta.getMetodoPago());
            ventasRepository.save(existente);
            return "Venta actualizada exitosamente";
        }).orElse("Venta no encontrada");
    }

    @Transactional
    public String eliminarVenta(Integer idVenta) {
        if (ventasRepository.existsById(idVenta)) {
            ventasRepository.deleteById(idVenta);
            return "Venta eliminada correctamente";
        }
        return "Venta no encontrada";
    }

    private VentasEntity mapToEntity(Ventas venta) {
        VentasEntity entity = new VentasEntity();
        entity.setCantProducto(venta.getCantProducto());
        entity.setTotalVenta(venta.getTotalVenta());
        entity.setFechaPago(venta.getFechaPago());
        entity.setMetodoPago(venta.getMetodoPago());
        return entity;
    }

    private Ventas mapToModel(VentasEntity entity) {
        return new Ventas(
                entity.getIdVenta(),
                entity.getCantProducto(),
                entity.getTotalVenta(),
                entity.getFechaPago(),
                entity.getMetodoPago()
        );
    }

    public VentasDto obtenerVentaPorId(Integer idVenta) {
        return ventasRepository.findById(idVenta)
                .map(venta -> new VentasDto(
                        venta.getIdVenta(),
                        venta.getCantProducto(),
                        venta.getTotalVenta(),
                        venta.getFechaPago(),
                        venta.getMetodoPago()
                ))
                .orElse(null);
    }
}