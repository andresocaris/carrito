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

@RestController
@RequestMapping("/compras")
public class CompraController {
	private final CompraService compraService;
	public CompraController(CompraService compraService){
		this.compraService=compraService;
	}
	@PostMapping("/mostrar-por-usuario")
	public ResponseEntity<List<Compra>>mostrarProductosPorUsuario(@RequestParam("usuario") Integer usuario){
		List<Compra> compras=compraService.comprasPorUsuario(usuario);
		return new ResponseEntity<>(compras,HttpStatus.OK);
	}
	
}
