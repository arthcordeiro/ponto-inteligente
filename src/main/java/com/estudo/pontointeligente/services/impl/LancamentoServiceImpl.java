package com.estudo.pontointeligente.services.impl;

import com.estudo.pontointeligente.dto.FuncionarioDTO;
import com.estudo.pontointeligente.dto.LancamentoDTO;
import com.estudo.pontointeligente.entities.Lancamento;
import com.estudo.pontointeligente.respositories.LancamentoRepository;
import com.estudo.pontointeligente.services.LancamentoService;
import com.estudo.pontointeligente.utils.DTOUtils;
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
    public Page<LancamentoDTO> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        log.info("Buscando lançamentos pelo id do funcionario {}", funcionarioId);
        return DTOUtils.build().toPageLancamentoDto(this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest));
    }

    @Override
    public Optional<LancamentoDTO> findById(Long id) {
        log.info("Buscando lançamento pelo Id {}", id);
        return Optional.ofNullable(DTOUtils.build().lancamentoToDto(this.lancamentoRepository.findById(id).get()));
    }

    @Override
    public LancamentoDTO save(LancamentoDTO lancamentoDTO) {
        log.info("Registrando um lançamento no banco {}", lancamentoDTO);
        Lancamento retorno = this.lancamentoRepository
                .save(DTOUtils.build().dtoToLancamento(lancamentoDTO));
        return DTOUtils.build().lancamentoToDto(retorno);
    }

    @Override
    public void delete(LancamentoDTO lancamentoDTO) {
        log.info("Removendo o lançamento {}", lancamentoDTO);
        this.lancamentoRepository.delete(DTOUtils.build().dtoToLancamento(lancamentoDTO));
    }
}
