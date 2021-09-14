package pe.edu.ao.carrito.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.ao.carrito.model.Compra;
import pe.edu.ao.carrito.service.CompraService;
import pe.edu.ao.carrito.service.UsuarioService;

@RestController
@RequestMapping("/compras")
public class CompraController {
	private final CompraService compraService;
	private final UsuarioService usuarioService;
	public CompraController(CompraService compraService, UsuarioService usuarioService){
		this.compraService=compraService;
		this.usuarioService=usuarioService;
	}
	@PostMapping("/mostrar-por-usuario")
	public ResponseEntity<List<Compra>>mostrarProductosPorUsuario(@RequestParam("usuario") String usuario){
		Integer idUsuario=usuarioService.ObtenerIdPorNombre(usuario);
		List<Compra> compras=compraService.comprasPorUsuario(idUsuario);
		return new ResponseEntity<>(compras,HttpStatus.OK);
	}
	
}
