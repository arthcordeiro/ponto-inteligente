package com.estudo.pontointeligente.services;

import com.estudo.pontointeligente.dto.LancamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LancamentoService {

    /**
     * Busca lançaento por Id do funcionario
     *
     * @param funcionarioId
     * @param pageRequest
     * @return Page<LancamentoDTO>
     */
    Page<LancamentoDTO> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    /**
     * Busca lançamento por ID
     *
     * @param id
     * @return Optional<LancamentoDTO>
     */
    Optional<LancamentoDTO> findById(Long id);

    /**
     * Salva um novo lançamento
     *
     * @param lancamentoDTO
     * @return LancamentoDTO
     */
    LancamentoDTO save(LancamentoDTO lancamentoDTO);

    /**
     * Remove um lançamento
     *
     * @param lancamentoDTO
     */
    void delete(LancamentoDTO lancamentoDTO);
}
