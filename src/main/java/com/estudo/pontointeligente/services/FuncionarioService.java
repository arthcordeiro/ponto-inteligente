package com.estudo.pontointeligente.services;

import com.estudo.pontointeligente.dto.FuncionarioDTO;

import java.util.Optional;

public interface FuncionarioService {

    /**
     * Salva um funcionário no banco de dados
     *
     * @param funcioarioDto
     * @return FuncionarioDTO
     */
    FuncionarioDTO save (FuncionarioDTO funcioarioDto);

    /**
     * Busca um funcionario pelo CPF
     *
     * @param cpf
     * @return Optional<FuncionarioDTO>
     */
    Optional<FuncionarioDTO> findByCpf(String cpf);

    /**
     * Busca um funcionário pelo e-mail
     *
     * @param email
     * @return Optional<FuncionarioDTO>
     */
    Optional<FuncionarioDTO> findByEmail(String email);

    /**
     *
     * @param id
     * @return Optional<FuncionarioDTO>
     */
    Optional<FuncionarioDTO> findById(Long id);
}
