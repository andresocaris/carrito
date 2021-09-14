package pe.edu.ao.carrito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.ao.carrito.interfaz.CompraRepo;
import pe.edu.ao.carrito.model. Compra;


@Service
public class CompraService {
	private final CompraRepo compraRepo;
	@Autowired
	public CompraService(CompraRepo compraRepo) {
		this.compraRepo = compraRepo;
	}
	public  Compra addCompra( Compra  Compra) {
		return  compraRepo.save( Compra);
	}
	public List<Compra> findAllCompra(){
		return  compraRepo.findAll();
	}	
	public List<Compra>comprasPorUsuario(Integer idUsuario){
		return compraRepo.findByIdUsuario(idUsuario);
	}
}
