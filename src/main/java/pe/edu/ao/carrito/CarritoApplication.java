package pe.edu.ao.carrito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.edu.ao.carrito.security.JWTAuthorizationFilter;

@SpringBootApplication
public class CarritoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarritoApplication.class, args);
		System.out.println("Welcome to carrito");
	}
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/obtener-usuario").permitAll()
				.antMatchers(HttpMethod.POST, "/compras/mostrar-por-usuario").permitAll()
				.antMatchers(HttpMethod.GET, "/compras/mostrar-productos-mas-vendidos/{\\d+}").permitAll()
				.antMatchers(HttpMethod.POST, "/compras/mostrar-por-usuario-paginacion/{\\d+}/{\\d+}").permitAll()
//				.antMatchers(HttpMethod.POST, "/producto/agregar-productos").permitAll()
				.anyRequest().authenticated();
		}
	}
}
