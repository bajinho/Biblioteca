/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bajo.biblioteca.bean.impl;

import com.bajo.biblioteca.model.Emprestimo;
import com.bajo.biblioteca.model.view.EmprestimoView;
import jakarta.ejb.Remote;
import java.util.List;

/**
 *
 * @author bajinho
 */


@Remote
public interface EmprestimoRemote {

    public Emprestimo salvar(Emprestimo emprestimo)
            throws Exception;

    public void excluir(Long id);

    public Emprestimo consultarPorId(Long id);
    
    public List<EmprestimoView> consultarPorTitulo(String titulo);
    
    public List<EmprestimoView> consultarPorNome(String nome);
}
