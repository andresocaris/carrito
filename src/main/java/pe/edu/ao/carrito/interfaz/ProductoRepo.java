package pe.edu.ao.carrito.interfaz;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.ao.carrito.model.Producto;

public interface ProductoRepo extends JpaRepository<Producto,Long>{

	Producto findProductoById(Long id);
	
}
