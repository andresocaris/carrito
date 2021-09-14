package pe.edu.ao.carrito.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.ao.carrito.interfaz.ProductoRepo;
import pe.edu.ao.carrito.model.Producto;
import pe.edu.ao.carrito.util.FuncionUtil;

@Service
public class ProductoService {
	private final ProductoRepo productoRepo;
	@Autowired
	public ProductoService(ProductoRepo employeeRepo) {
	    this.productoRepo = employeeRepo;
	}
	public Producto addProducto(Producto producto) {
		return productoRepo.save(producto);
	}
	public List<Producto> findAllProducto(){
		return productoRepo.findAll();
	}
	public Producto findProductoByNombre(String nombre) {
		return productoRepo.findProductoByName(nombre);
	}
	public Producto findProductoById(Integer idProducto) {
		return productoRepo.findProductoById(idProducto);
	}
	public List<Producto> findProductoPorPaginacion(Integer cantidadPorPagina, Integer numeroPagina ){
		List<Producto> productos = productoRepo.findAll();
		List<Producto> productosMostrados = new ArrayList<>();
		for (int i=cantidadPorPagina*(numeroPagina-1);i<cantidadPorPagina*numeroPagina;i++) {
			productosMostrados.add(productos.get(i));
		}
		return productosMostrados;
	}
	public Producto editarProducto(Producto producto) {
		Producto productoEditado= productoRepo.findProductoById(producto.getId());
		Integer estadoProducto = productoEditado.getEstado();
		
		FuncionUtil util = new FuncionUtil();
		BeanUtils.copyProperties(producto, productoEditado, util.getNullPropertyNames(producto));
		productoEditado.setEstado(estadoProducto);
		
		System.out.println("el producto editado es:"+productoEditado);
		return productoRepo.save(productoEditado);
	}
	public Producto eliminarProducto(Integer id) {
		Producto productoEliminado =  productoRepo.findProductoById(id);
		productoEliminado.setEstado(0);
		return productoRepo.save(productoEliminado);
	}
}
