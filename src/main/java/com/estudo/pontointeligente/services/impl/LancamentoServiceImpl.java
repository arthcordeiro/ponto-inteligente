package com.estudo.pontointeligente.services.impl;

import com.estudo.pontointeligente.entities.Lancamento;
import com.estudo.pontointeligente.respositories.LancamentoRepository;
import com.estudo.pontointeligente.services.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LancamentoServiceImpl implements LancamentoService {

    private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Page<Lancamento> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        log.info("Buscando lançamentos pelo id do funcionario {}", funcionarioId);
        return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    @Override
    public Optional<Lancamento> findById(Long id) {
        log.info("Buscando lançamento pelo Id {}", id);
        return this.lancamentoRepository.findById(id);
    }

    @Override
    public Lancamento save(Lancamento lancamento) {
        log.info("Registrando um lançamento no banco {}", lancamento);
        return this.lancamentoRepository
                .save(lancamento);
    }

    @Override
    public void delete(Lancamento lancamento) {
        log.info("Removendo o lançamento {}", lancamento);
        this.lancamentoRepository.delete(lancamento);
    }
}
