package com.api.producto.repository;

import com.producto.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductoRepository extends JpaRepository<Producto, Integer> {

}
