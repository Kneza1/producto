package com.api.producto.controllers;

import com.api.producto.dto.ProductoDTO;
import com.api.producto.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo; 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Integer id, @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public ProductoDTO obtenerHATEOAS(@PathVariable Integer id) { 
        ProductoDTO dto = service.obtenerPorId(id);
        // Agregar enlaces HATEOAS 
        dto.add(linkTo(methodOn(ProductoController.class).obtenerHATEOAS(id)).withSelfRel()); 
        dto.add(linkTo(methodOn(ProductoController.class).obtenerTodosHATEOAS()).withRel("todos")); 
        dto.add(linkTo(methodOn(ProductoController.class).eliminar(id)).withRel("eliminar")); 
        return dto;
    }
    @GetMapping("/hateoas") 
    public List<ProductoDTO> obtenerTodosHATEOAS() { 
        List<ProductoDTO> lista = service.listar(); 
            for (ProductoDTO dto : lista) { 
            dto.add(linkTo(methodOn(ProductoController.class).obtenerHATEOAS(dto.getId())).withSelfRel());
    } 
    return lista; 
} 
}
