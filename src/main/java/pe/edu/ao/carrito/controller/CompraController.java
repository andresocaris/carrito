package pe.edu.ao.carrito.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.ao.carrito.model.Compra;
import pe.edu.ao.carrito.model.Producto;
import pe.edu.ao.carrito.service.CompraService;
import pe.edu.ao.carrito.service.ProductoService;
import pe.edu.ao.carrito.service.UsuarioService;

@RestController
@RequestMapping("/compras")
public class CompraController {
	private final CompraService compraService;
	private final UsuarioService usuarioService;
	private final ProductoService productoService;
	public CompraController(CompraService compraService, UsuarioService usuarioService,ProductoService productoService){
		this.compraService=compraService;
		this.usuarioService=usuarioService;
		this.productoService=productoService;
	}
	@PostMapping("/mostrar-por-usuario")
	public ResponseEntity<ArrayList<HashMap<String,Object>>>mostrarProductosPorUsuario(@RequestParam("usuario") String usuario){
		Integer idUsuario=usuarioService.ObtenerIdPorNombre(usuario);
		List<Compra> compras=compraService.comprasPorUsuario(idUsuario);
		ArrayList<HashMap<String,Object>> listaProductos = new  ArrayList<>();
		for (Compra compra:compras) {
			HashMap<String,Object> miHashMap= new HashMap<String,Object>();
			Integer idProducto=compra.getIdProducto();
			Producto producto = productoService.findProductoById(idProducto);
			
			String nameProducto=producto.getName();
			Integer costoProducto=producto.getCosto();
			Integer cantidad=compra.getCantidad();
			Date fecha=compra.getFecha();
			
			miHashMap.put("nombre-producto", nameProducto);
			miHashMap.put("costo-producto", costoProducto);
			miHashMap.put("cantidad", cantidad);
			miHashMap.put("fecha de compra", fecha);
			
			listaProductos.add(miHashMap);
		}
		
		return new ResponseEntity<>(listaProductos,HttpStatus.OK);
	}
	@PostMapping("/mostrar-por-usuario-paginacion/{cantidadPorPagina}/{numeroPagina}")
	public ResponseEntity<ArrayList<HashMap<String,Object>>>mostrarProductosPorUsuarioPaginacion(
			@PathVariable Integer cantidadPorPagina ,@PathVariable Integer numeroPagina,@RequestParam("usuario") String usuario){
		Integer idUsuario=usuarioService.ObtenerIdPorNombre(usuario);
		List<Compra> compras=compraService.comprasPorUsuario(idUsuario);
		ArrayList<HashMap<String,Object>> listaProductos = new  ArrayList<>();
		for (Compra compra:compras) {
			HashMap<String,Object> miHashMap= new HashMap<String,Object>();
			Integer idProducto=compra.getIdProducto();
			Producto producto = productoService.findProductoById(idProducto);
			
			String nameProducto=producto.getName();
			Integer costoProducto=producto.getCosto();
			Integer cantidad=compra.getCantidad();
			Date fecha=compra.getFecha();
			
			miHashMap.put("nombre-producto", nameProducto);
			miHashMap.put("costo-producto", costoProducto);
			miHashMap.put("cantidad", cantidad);
			miHashMap.put("fecha de compra", fecha);
			listaProductos.add(miHashMap);
		}
		ArrayList<HashMap<String,Object>> comprasMostradas = new ArrayList<>();
		for (Integer i=cantidadPorPagina*(numeroPagina-1);i<cantidadPorPagina*numeroPagina;i++) {
			comprasMostradas.add(listaProductos.get(i));
		}
		return new ResponseEntity<>(comprasMostradas,HttpStatus.OK);
	}
	@GetMapping("/mostrar-productos-mas-vendidos/{numeroProductos}")
	public ResponseEntity<Object> mostrarDiezProductosMasVendidos(@PathVariable Integer numeroProductos){
		Map<String,Integer> hashMap = new HashMap<String,Integer>();
		List<Compra> compras = compraService.findAllCompra();
		for (Compra compra:compras) {
			Integer idProducto = compra.getIdProducto();
			String nombreProducto=productoService.findProductoById(idProducto).getName();
			Integer cantidadProducto=compra.getCantidad();
			if (!hashMap.containsKey(nombreProducto)) hashMap.put( nombreProducto,cantidadProducto);
			else {
				Integer valorAnterior = hashMap.containsKey(nombreProducto)?hashMap.get(nombreProducto):0;
				hashMap.put( nombreProducto, valorAnterior+cantidadProducto);
			}
		}
		Map<Integer, String> treeMap = new TreeMap<Integer, String>();
		for (Map.Entry<String, Integer> entry:hashMap.entrySet()) {
			treeMap.put(-entry.getValue(),entry.getKey());
		}
		Map<String, Integer> treeMap2 = new LinkedHashMap<String, Integer>();
		int cantidad=0;
		for (Map.Entry<Integer, String> entry:treeMap.entrySet()) {
			treeMap2.put(entry.getValue(),-entry.getKey());
			if (cantidad == numeroProductos-1 )break;
			cantidad++;
		}
		return new ResponseEntity<>(treeMap2,HttpStatus.OK);
	}
}
