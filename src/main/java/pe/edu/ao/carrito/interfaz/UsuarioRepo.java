package pe.edu.ao.carrito.interfaz;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.ao.carrito.model.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario,Integer>{

	Usuario findByNombreAndPwd(String nombre, String contrasena);

}
