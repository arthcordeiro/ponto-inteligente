package com.estudo.pontointeligente.services;

import com.estudo.pontointeligente.entities.Funcionario;

import java.util.Optional;

public interface FuncionarioService {

    /**
     * Salva um funcionário no banco de dados
     *
     * @param funcionario
     * @return Funcionario
     */
    Funcionario save (Funcionario funcionario);

    /**
     * Busca um funcionario pelo CPF
     *
     * @param cpf
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> findByCpf(String cpf);

    /**
     * Busca um funcionário pelo e-mail
     *
     * @param email
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> findByEmail(String email);

    /**
     *
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> findById(Long id);
}
