package com.ventas.principal.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentasDto {
    private Integer idVenta;
    private Integer cantProducto;
    private Integer totalVenta;
    private String fechaPago;
    private String metodoPago;
}
