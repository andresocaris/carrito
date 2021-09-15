package pe.edu.ao.carrito.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import pe.edu.ao.carrito.exception.ProductoException;
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
    public ResponseEntity<HashMap<String,Object>> addEmployee(@RequestBody Producto producto) {
		HashMap<String,Object> data = new HashMap<String,Object>();
		try {
			producto.setEstado(1);
	        Producto newProducto = productoService.addProducto(producto);
	        data.put("success", true);
	        data.put("msg", newProducto);
	        return new ResponseEntity<>(data, HttpStatus.CREATED);
		}catch(Exception e) {
			data.put("success", true);
			data.put("msg", e.getMessage());
			return new ResponseEntity<>(data, HttpStatus.CREATED);
		}
    }
	@PutMapping("/update")
	public ResponseEntity<HashMap<String,Object>> editarProducto(@RequestBody Producto producto){
		HashMap<String,Object> data = new HashMap<String,Object>();
		try {
			Producto productoEditado = productoService.editarProducto(producto);
			data.put("success", true);
			data.put("msg", productoEditado);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			data.put("success", false);
			data.put("msg",e.getMessage() );
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	@PutMapping("/delete/{id}")
	public ResponseEntity<HashMap<String,Object>> eliminarProducto(@PathVariable("id") Integer id ) throws ProductoException{
		HashMap<String,Object> data = new HashMap<String,Object>();
		try {
			Producto productoEliminado = productoService.eliminarProducto(id);
			data.put("success","true");
			data.put("msg", productoEliminado);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			data.put("success","false");
			data.put("msg", e.getMessage());
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	@GetMapping("/all")
	public ResponseEntity<HashMap<String,Object>>obtenerProductos(HttpServletRequest request){
		HashMap<String,Object> data = new HashMap<String,Object>();
		try {
			List<Producto> productos=productoService.findAllProducto();
			HttpSession misession= request.getSession();
			List<Producto> productosSalida2 = (List<Producto>) misession.getAttribute("productos");
			if (productosSalida2==null) {
				List<Producto> productosAIngresar=productoService.findAllProducto();
				misession.setAttribute("productos", productosAIngresar);
			}else {
				List<Producto> productosAIngresar=productoService.findAllProducto();
				for (Producto x : productosAIngresar) {
					productosSalida2.add(x);
				}	
				misession.setAttribute("productos", productosSalida2);
			}
			data.put("success", true);
			data.put("msg:", productos);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			data.put("success", false);
			data.put("msg", e.getMessage());	
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	@GetMapping("/all/{cantidadPorPagina}/{numeroDePagina}")
	public ResponseEntity<HashMap<String,Object> > mostrarPorPagina
	( @PathVariable("cantidadPorPagina") Integer cantidadPorPagina, @PathVariable("numeroDePagina") Integer numeroDePagina ){
		HashMap<String,Object> data = new HashMap<String,Object>();
		try {
			List<Producto> productos = productoService.findProductoPorPaginacion(cantidadPorPagina,numeroDePagina);
			data.put("success", true);
			data.put("msg:", productos);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			data.put("success", false);
			data.put("msg", e.getMessage());	
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	@PostMapping("/agregar-productos")
	public ResponseEntity<HashMap<String,Object>> agregarProductos(HttpServletRequest request,@RequestBody HashMap<String,HashMap<String,Integer> > productos){
		HashMap<String,Object> data = new HashMap<String,Object>();
		try {
			HttpSession miSession = request.getSession();
			if (miSession.getAttribute("usuario")==null) {
				data.put("success", false);
				data.put("msg", "no has iniciado sesion o no has agregados el token");
				return new ResponseEntity<>(data,HttpStatus.OK);
			}
			//System.out.println("valor:   "+miSession.getAttribute("productos"));
			HashMap<String,Integer> misProductos =  (HashMap<String, Integer>) miSession.getAttribute("productos");
			HashMap<String,Integer> productosIngresados = productos.get("productos");
			HashMap<String,Integer> misProductosActualizado= productoService.agregarProductosAlHashMap(misProductos, productosIngresados);
			miSession.setAttribute("productos", misProductosActualizado);
			data.put("success", true);
			data.put("msg", misProductosActualizado);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			data.put("msg", e.getMessage());
			data.put("success", false);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	@PostMapping("/quitar-productos")
	public ResponseEntity<HashMap<String,Object>> eliminarProductos(HttpServletRequest request,@RequestBody HashMap<String,HashMap<String,Integer> > productos){
		HashMap<String,Object> data = new HashMap<String,Object>();
		try {
			HttpSession miSession = request.getSession();
			if (miSession.getAttribute("usuario")==null) {
				data.put("success", false);
				data.put("msg", "no has iniciado sesion o no has agregado el token");
				return new ResponseEntity<>(data,HttpStatus.OK);
			}
			HashMap<String,Integer> misProductos = (HashMap<String, Integer>) miSession.getAttribute("productos");
			HashMap<String,Integer> productosIngresados = productos.get("productos");
			HashMap<String,Integer> productosActualizados=productoService.quitarproductosHashMap(misProductos,productosIngresados);
			miSession.setAttribute("productos", productosActualizados);
			data.put("msg", productosActualizados);
			data.put("success", true);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			data.put("msg", e.getMessage());
			data.put("success", false);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	@PostMapping("/generar-compra-del-usuario")
	public ResponseEntity<HashMap<String,Object>> generarCompra(HttpServletRequest request,@RequestBody HashMap<String,HashMap<String,Integer> > productos){
		HashMap<String,Object> data = new HashMap<String,Object>();
		try {
			HttpSession miSession = request.getSession();		
			if (miSession.getAttribute("usuario")==null) {
				data.put("success", false);
				data.put("msg", "no has iniciado sesion o no has agregado el token");
				return new ResponseEntity<>(data,HttpStatus.OK);
			}
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
			data.put("msg", output);
			data.put("success",true);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			data.put("msg", e.getMessage());
			data.put("success", false);
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}	
}
