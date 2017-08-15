package br.com.caelum.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import br.com.caelum.model.Categoria;
import org.springframework.stereotype.Repository;

import br.com.caelum.model.Loja;
import br.com.caelum.model.Produto;

@Repository
public class ProdutoDao {

	@PersistenceContext
	private EntityManager em;

	public List<Produto> getProdutos() {
		return em.createQuery("from Produto", Produto.class).getResultList();
	}

	public Produto getProduto(Integer id) {
		return em.find(Produto.class, id);
	}

	public List<Produto> getProdutos(String nome, Integer categoriaId, Integer lojaId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> produtoRoot = query.from(Produto.class);

		Predicate conjuncao = builder.conjunction();

		if (!nome.isEmpty()) {
			Path<String> nomeProduto = produtoRoot.get("nome");
			Predicate nomeIgual = builder.like(nomeProduto, "%" + nome + "%");

			conjuncao = builder.and(nomeIgual);
		}

		if (categoriaId != null) {
			Join<Produto, List<Categoria>> join = produtoRoot.join("categorias");
			Path<Integer> categoriaProduto = join.get("id");

			conjuncao = builder.and(conjuncao,
					builder.equal(categoriaProduto, categoriaId));
		}

		if (lojaId != null) {
			Path<Loja> loja = produtoRoot.get("loja");
			Path<Integer> id = loja.get("id");

			conjuncao = builder.and(conjuncao, builder.equal(id, lojaId));
		}

		TypedQuery<Produto> typedQuery = em.createQuery(query.where(conjuncao));
		return typedQuery.getResultList();

	}

	public void insere(Produto produto) {
		if (produto.getId() == null)
			em.persist(produto);
		else
			em.merge(produto);
	}

}
