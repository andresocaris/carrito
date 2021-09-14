package pe.edu.ao.carrito.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Compra {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
	private Integer id;
	private Integer idUsuario;
	private Integer idProducto;
	private Integer cantidad;
	private Date Fecha;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto2) {
		this.idProducto = idProducto2;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidadProducto) {
		this.cantidad = cantidadProducto;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	@Override
	public String toString() {
		return "Compra [id=" + id + ", idUsuario=" + idUsuario + ", idProducto=" + idProducto + ", cantidad=" + cantidad
				+ ", Fecha=" + Fecha + "]";
	}
}
