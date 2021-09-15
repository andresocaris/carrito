package pe.edu.ao.carrito.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
	@Autowired
	ProductoService productoService;
	public List<String>categoriaMasDemanda(List<Compra> compras,Integer cantidadCategoria){
		Map<String,Integer> hashMap= new HashMap<>();
		for (Compra compra:compras) {
			Integer idProducto=compra.getIdProducto();
			
			String categoria=productoService.findProductoById(idProducto).getCategoria();
			Integer cantidad=compra.getCantidad();
			if (!hashMap.containsKey(categoria)) hashMap.put(categoria, cantidad);
			else {
				Integer cantidadAnterior=hashMap.get(categoria);
				hashMap.put(categoria, cantidadAnterior+cantidad);
			}
		}
		Map<Integer,String> treeMap= new TreeMap<>();
		for (Map.Entry<String,Integer> entry:hashMap.entrySet()) {
			treeMap.put(-entry.getValue(),entry.getKey());
		}
		List<String> categorias = new ArrayList<>();
		Integer contador=0;
		for (Map.Entry<Integer,String> entry:treeMap.entrySet()) {
			if (contador==cantidadCategoria)break;
			categorias.add(entry.getValue());
			contador++;
		}
		return categorias;
	}
}
