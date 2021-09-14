package pe.edu.ao.carrito.interfaz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.ao.carrito.model.Compra;

public interface CompraRepo extends JpaRepository<Compra,Integer>{

	List<Compra> findByIdUsuario(Integer idUsuario);

}
