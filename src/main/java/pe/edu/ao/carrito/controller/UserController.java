package pe.edu.ao.carrito.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pe.edu.ao.carrito.model.User;
import pe.edu.ao.carrito.model.Usuario;
import pe.edu.ao.carrito.service.UsuarioService;


@RestController
public class UserController {
	private final UsuarioService usuarioService;
	public UserController(UsuarioService usuarioService) {
		this.usuarioService=usuarioService;
	}
	@PostMapping("/obtener-usuario")
	public User login( HttpServletRequest request,@RequestParam("user") String username, @RequestParam("password") String pwd) {
		
		Usuario usuario = usuarioService.busquedaPorNombreContrasena(username, pwd); 
		if (usuario == null) {
			return new User("no existe","no existe");
		}else {
			String token=getJWTToken(username);
			User user = new User();
			user.setUser(username);
			user.setToken(token);
			HttpSession miSession = request.getSession();
			miSession.setAttribute("usuario", username);
			miSession.setAttribute("idUsuario", usuario.getId());
			return user;
		}
	}
	private String getJWTToken(String username) {
		String secretKey="mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		String token = Jwts.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
