package pe.edu.ao.carrito.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Producto {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;
    private String name;
    private Integer costo;
    private String categoria;
    private Integer estado;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCosto() {
		return costo;
	}
	public void setCosto(Integer costo) {
		this.costo = costo;
	}


	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", name=" + name + ", costo=" + costo + ", categoria=" + categoria + ", estado="
				+ estado + "]";
	}    
}
