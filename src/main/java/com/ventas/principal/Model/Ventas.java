package com.ventas.principal.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ventas {
    private Integer idVenta;

    @NotNull(message = "La cantidad de producto no puede ser nula")
    @Min(value = 1, message = "La cantidad de producto debe ser al menos 1")
    private Integer cantProducto;

    @NotNull(message = "El total de la venta no puede ser nulo")
    @Positive(message = "El total de la venta debe ser positivo")
    private Integer totalVenta;

    @NotNull(message = "La fecha de pago no puede ser nula")
    @Size(max = 20, message = "La fecha de pago no puede tener más de 20 caracteres")
    private String fechaPago;

    @NotNull(message = "El método de pago no puede ser nulo")
    @Size(max = 50, message = "El método de pago no puede tener más de 50 caracteres")
    private String metodoPago;
}
