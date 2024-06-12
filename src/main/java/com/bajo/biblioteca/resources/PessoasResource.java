/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bajo.biblioteca.resources;

import com.bajo.biblioteca.dao.PessoaDAO;
import com.bajo.biblioteca.model.Pessoa;
import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author bajinho
 */
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@DeclareRoles("user")
@Path("/pessoa")
public class PessoasResource {

    @PersistenceContext
    private EntityManager em;
    
    @GET
    public Response getAll() {
        PessoaDAO dao = new PessoaDAO(em);
        return Response.ok(dao.getAll()).build();
    }

    @GET
    @Path("{name}")
    public Response getUser(@PathParam("name") String name) {
        PessoaDAO dao = new PessoaDAO(em);
        return Response.ok(dao.consultarPorNome(name)).build();
    }

    @POST
    public Response create(Pessoa pessoa) throws Exception {
        PessoaDAO dao = new PessoaDAO(em);
        dao.salvar(pessoa);
        return Response.ok().build();
    }
}
