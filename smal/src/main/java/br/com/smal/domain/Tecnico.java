package br.com.smal.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tecnico")
@DiscriminatorValue("tecnico")
public class Tecnico extends Usuario {

	@OneToOne
	private Administrador administrador;

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
}