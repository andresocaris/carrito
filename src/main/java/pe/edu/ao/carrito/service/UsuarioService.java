package pe.edu.ao.carrito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.ao.carrito.interfaz.UsuarioRepo;
import pe.edu.ao.carrito.model.Usuario;

@Service
public class UsuarioService {
	private final UsuarioRepo usuarioRepo;
	@Autowired
	public UsuarioService(UsuarioRepo usuarioRepo) {
		this.usuarioRepo=usuarioRepo;
	}
	public Usuario busquedaPorNombreContrasena(String nombre,String contrasena) {
		return usuarioRepo.findByNombreAndPwd(nombre,contrasena);
	}
	public Integer ObtenerIdPorNombre(String nombre) throws Exception {
		Usuario usuario = usuarioRepo.getByNombre(nombre);
		if (usuario==null){
			throw new Exception("No existe Usuario");
		}
		return usuario.getId();
	}
}
