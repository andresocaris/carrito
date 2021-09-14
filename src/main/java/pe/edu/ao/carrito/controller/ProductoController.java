package pe.edu.ao.carrito.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.ao.carrito.model.Compra;
import pe.edu.ao.carrito.model.Producto;
import pe.edu.ao.carrito.service.CompraService;
import pe.edu.ao.carrito.service.ProductoService;


@RestController
@RequestMapping("/producto")
public class ProductoController {
	private final ProductoService productoService;
	private final CompraService compraService;
	public ProductoController(ProductoService productoService,CompraService compraService ) {
		this.productoService=productoService;
		this.compraService=compraService;
	}
	@PostMapping("/add")
    public ResponseEntity<Producto> addEmployee(@RequestBody Producto producto) {
		producto.setEstado(1);
        Producto newProducto = productoService.addProducto(producto);
        return new ResponseEntity<>(newProducto, HttpStatus.CREATED);
    }
	@PutMapping("/update")
	public ResponseEntity<Producto> editarProducto(@RequestBody Producto producto){
		Producto productoEditado = productoService.editarProducto(producto);
		return new ResponseEntity<>(productoEditado,HttpStatus.OK);
	}
	@PutMapping("/delete/{id}")
	public ResponseEntity<Producto> eliminarProducto(@PathVariable("id") Integer id ){
		Producto productoEliminado = productoService.eliminarProducto(id);
		return new ResponseEntity<>(productoEliminado,HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<List<Producto>> obtenerProductos(HttpServletRequest request){
		List<Producto> productos=productoService.findAllProducto();
		HttpSession misession= request.getSession();
		List<Producto> productosSalida2 = (List<Producto>) misession.getAttribute("productos");
		if (productosSalida2==null) {
			List<Producto> productosAIngresar=productoService.findAllProducto();
			System.out.println("es nullo los productos");
			misession.setAttribute("productos", productosAIngresar);
		}else {
			List<Producto> productosAIngresar=productoService.findAllProducto();
			
			for (Producto x : productosAIngresar) {
				productosSalida2.add(x);
			}

			misession.setAttribute("productos", productosSalida2);
			
			System.out.println("no es nullo y los productos en mi session son");
			System.out.println(productosSalida2);
		}
		return new ResponseEntity<>(productos,HttpStatus.OK);
	}
	@GetMapping("/all/{cantidadPorPagina}/{numeroDePagina}")
	public ResponseEntity<List<Producto>> mostrarPorPagina
	( @PathVariable("cantidadPorPagina") Integer cantidadPorPagina, @PathVariable("numeroDePagina") Integer numeroDePagina ){
		
		List<Producto> productos = productoService.findProductoPorPaginacion(cantidadPorPagina,numeroDePagina);
		return new ResponseEntity<>(productos,HttpStatus.OK);
	}
	@PostMapping("/agregar-productos")
	public ResponseEntity<Object> agregarProductos(HttpServletRequest request,@RequestBody HashMap<String,HashMap<String,Integer> > productos){
		HttpSession miSession = request.getSession();
		
		HashMap<String,Integer> misProductos = (HashMap<String, Integer>) miSession.getAttribute("productos");
		HashMap<String,Integer> productosIngresados = productos.get("productos");
		
		if (misProductos == null) {
			misProductos = productosIngresados;
		}else {
			for (Map.Entry<String, Integer> producto : productosIngresados.entrySet()) {
				Integer cantidadAnterior = misProductos.get(producto.getKey())==null?0:misProductos.get(producto.getKey());
				misProductos.put(producto.getKey(), cantidadAnterior + producto.getValue());
			}
		}
		miSession.setAttribute("productos", misProductos);
		return new ResponseEntity<>(misProductos,HttpStatus.OK);
	}
	@PostMapping("/quitar-productos")
	public ResponseEntity<Object> eliminarProductos(HttpServletRequest request,@RequestBody HashMap<String,HashMap<String,Integer> > productos){
		HttpSession miSession = request.getSession();
		
		HashMap<String,Integer> misProductos = (HashMap<String, Integer>) miSession.getAttribute("productos");
		HashMap<String,Integer> productosIngresados = productos.get("productos");
		
		if (misProductos != null) {
			for (Map.Entry<String, Integer> producto : productosIngresados.entrySet()) {
				Integer cantidadAnterior = misProductos.get(producto.getKey())==null?0:misProductos.get(producto.getKey());
				Integer cantidadActual=cantidadAnterior-producto.getValue()<0?0:cantidadAnterior-producto.getValue();
				if (cantidadActual>0)misProductos.put(producto.getKey(), cantidadActual);
				else misProductos.remove(producto.getKey());
			}
		}
		miSession.setAttribute("productos", misProductos);
		return new ResponseEntity<>(misProductos,HttpStatus.OK);
	}
	@PostMapping("/generar-compra-del-usuario")
	public ResponseEntity<HashMap<Object,Object>> generarCompra(HttpServletRequest request,@RequestBody HashMap<String,HashMap<String,Integer> > productos){
		HttpSession miSession = request.getSession();
		
		HashMap<String,Integer> misProductos = (HashMap<String, Integer>) miSession.getAttribute("productos");
		HashMap<Object,Object> output = new HashMap<Object,Object>();
		
		output.put("se ha generado una compra del usuario con los siguientes productos", misProductos);
		output.put("usuario", miSession.getAttribute("usuario"));
		
		Integer idUsuario=(Integer) miSession.getAttribute("idUsuario");
		Date date = new Date();
		for (Entry<String, Integer> producto : misProductos.entrySet()) {
			String nombreProducto = producto.getKey();
			Integer cantidadProducto = producto.getValue();
			Compra compra = new Compra();
			Integer idProducto;
			idProducto = productoService.findProductoByNombre(nombreProducto).getId();
			
			compra.setIdProducto(idProducto);
			compra.setCantidad(cantidadProducto);
			compra.setFecha(date);
			compra.setIdUsuario(idUsuario);
			compraService.addCompra(compra);
		}
		return new ResponseEntity<>(output,HttpStatus.OK);
	}	
}
