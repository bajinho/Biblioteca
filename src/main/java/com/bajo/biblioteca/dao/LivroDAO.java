/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bajo.biblioteca.dao;

import com.bajo.biblioteca.model.Livro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author bajinho
 */
/**
 * Classe utilizada para realizar as operações com o bando de dados.
 */
public class LivroDAO {

    private final EntityManager entityManager;

    /**
     * Construtor da classe DAO que chama os métodos do EntityManager.
     *
     * @param entityManager
     */
    public LivroDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método para salvar ou atualizar o livro.
     *
     * @param livro
     * @return
     * @throws java.lang.Exception
     */
    public Livro salvar(Livro livro) throws Exception {
        System.out.println("Salvando o livro: "
                + livro.getTitulo());

        /* Verifica se o livro ainda não está salvo no banco
      de dados. */
        if (livro.getId() == null) {
            /* Salva o livro no banco de dados. */
            this.entityManager.persist(livro);
        } else {
            /* Verifica se o livro não está no estado managed. */
            if (!this.entityManager.contains(livro)) {
                /* Se o livro não está no estado managed verifica se 
          ele existe na base. */
                if (entityManager.find(Livro.class, livro.getId())
                        == null) {
                    throw new Exception("Livro existe!");
                }
            }
            /* Faz uma atualização do livro que estava gravado na base
        de dados. */
            return entityManager.merge(livro);
        }

        /* Retorna o livro que foi salvo, este retorno ocorre para
      podermos ter o id que foi salvo. */
        return livro;
    }

    /**
     * Método que exclui o livro do banco de dados.
     *
     * @param id
     */
    public void excluir(Long id) {
        /* Consulta o livro na base de dados através 
      de seu ID. */
        Livro livro = entityManager.find(Livro.class, id);
        System.out.println("Excluindo o livro: "
                + livro.getTitulo());

        /* Remove o livro da base de dados. */
        entityManager.remove(livro);
    }

    /**
     * Método que consulta o livro pelo ID.
     *
     * @param id
     * @return
     */
    public Livro consultarPorId(Long id) {
        return entityManager.find(Livro.class, id);
    }

    /**
     * Consulta a livro por Titulo.
     *
     * @param titulo
     * @return
     */
    public List<Livro> consultarPorTitulo(String titulo) {
        TypedQuery<Livro> query
                = entityManager.createNamedQuery("Livro.findByTitulo", Livro.class);
        return query.setParameter("titulo", "%" + titulo + "%").getResultList();
    }
    
    
    /**
     * Consulta todos livros cadastrados.
     *
     * @return
     */
    public List<Livro> getAll() {
        TypedQuery<Livro> query
                = entityManager.createNamedQuery("Livro.findAll", Livro.class);
        return query.getResultList();
    }
}
