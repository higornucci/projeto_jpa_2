
package br.com.caelum.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	private String nome;
	@NotEmpty
	private String linkDaFoto;
	@NotEmpty
	@Column(columnDefinition="TEXT")
	private String descricao;
	@Min(20)
	private double preco;
	@Valid
	@ManyToOne
	private Loja loja;
	@ManyToMany
	@JoinTable(name="CATEGORIA_PRODUTO")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<Categoria> categorias = new ArrayList<>();
	@Version
	private int versao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	//método auxiliar para associar categorias com o produto
	//se funcionar apos ter definido o relacionamento entre produto e categoria
	public void adicionarCategorias(Categoria... categorias) {
		this.categorias.addAll(Arrays.asList(categorias));
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public String getLinkDaFoto() {
		return linkDaFoto;
	}
	
	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public void setLinkDaFoto(String linkDaFoto) {
		this.linkDaFoto = linkDaFoto;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Loja getLoja() {
		return loja;
	}

	public int getVersao() {
		return versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}
}
