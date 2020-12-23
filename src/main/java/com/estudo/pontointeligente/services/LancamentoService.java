package com.estudo.pontointeligente.services;

import com.estudo.pontointeligente.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LancamentoService {

    /**
     * Busca lançaento por Id do funcionario
     *
     * @param funcionarioId
     * @param pageRequest
     * @return Page<Lancamento>
     */
    Page<Lancamento> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    /**
     * Busca lançamento por ID
     *
     * @param id
     * @return Optional<Lancamento>
     */
    Optional<Lancamento> findById(Long id);

    /**
     * Salva um novo lançamento
     *
     * @param lancamento
     * @return Lancamento
     */
    Lancamento save(Lancamento lancamento);

    /**
     * Remove um lançamento
     *
     * @param lancamento
     */
    void delete(Lancamento lancamento);
}
