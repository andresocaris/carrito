package pe.edu.ao.carrito.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.ao.carrito.model.Producto;
import pe.edu.ao.carrito.service.ProductoService;


@RestController
@RequestMapping("/producto")
public class ProductoController {
	private final ProductoService productoService;
	public ProductoController(ProductoService productoService ) {
		this.productoService=productoService;
	}
	
	@PostMapping("/add")
    public ResponseEntity<Producto> addEmployee(@RequestBody Producto producto) {
		producto.setEstado(1);
        Producto newProducto = productoService.addProducto(producto);
        return new ResponseEntity<>(newProducto, HttpStatus.CREATED);
    }
	
	@GetMapping("/all")
	public ResponseEntity<List<Producto>> obtenerProductos(){
		List<Producto> productos = productoService.findAllProducto();
		return new ResponseEntity<>(productos,HttpStatus.OK);
	}
	@GetMapping("/all/{cantidadPorPagina}/{numeroDePagina}")
	public ResponseEntity<List<Producto>> mostrarPorPagina
	( @PathVariable("cantidadPorPagina") Integer cantidadPorPagina, @PathVariable("numeroDePagina") Integer numeroDePagina ){
		
		List<Producto> productos = productoService.findProductoPorPaginacion(cantidadPorPagina,numeroDePagina);
		return new ResponseEntity<>(productos,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Producto> editarProducto(@RequestBody Producto producto){
		Producto productoEditado = productoService.editarProducto(producto);
		return new ResponseEntity<>(productoEditado,HttpStatus.OK);
	}
	
}
