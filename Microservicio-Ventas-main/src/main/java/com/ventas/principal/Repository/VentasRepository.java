package com.ventas.principal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ventas.principal.Model.entity.VentasEntity;

@Repository
public interface VentasRepository extends JpaRepository<VentasEntity, Integer>{

    VentasEntity findByIdVenta(int idVenta);
    Boolean existsByIdVenta(int idVenta);
    void deleteByIdVenta(int idVenta);
    VentasEntity findBycantProducto(int cantProducto);

}