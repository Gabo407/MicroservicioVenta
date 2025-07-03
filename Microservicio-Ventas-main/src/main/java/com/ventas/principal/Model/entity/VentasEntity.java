package com.ventas.principal.Model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "ventas")
@Data
public class VentasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenta;

    @Column(name = "cantProducto", nullable = false)
    @NotNull(message = "La cantidad de producto no puede ser nula")
    @Min(value = 1, message = "La cantidad de producto debe ser al menos 1")
    private Integer cantProducto;

    @Column(name = "totalVenta", nullable = false)
    @NotNull(message = "El total de la venta no puede ser nulo")
    @Positive(message = "El total de la venta debe ser positivo")
    private Integer totalVenta;

    @Column(name = "fechaPago", nullable = false, length = 20)
    @NotNull(message = "La fecha de pago no puede ser nula")
    @Size(max = 20, message = "La fecha de pago no puede tener más de 20 caracteres")
    private String fechaPago;

    @Column(name = "metodoPago", nullable = false, length = 50)
    @NotNull(message = "El método de pago no puede ser nulo")
    @Size(max = 50, message = "El método de pago no puede tener más de 50 caracteres")
    private String metodoPago;
}