package com.estudo.pontointeligente.services.impl;

import com.estudo.pontointeligente.dto.FuncionarioDTO;
import com.estudo.pontointeligente.respositories.FuncionarioRepository;
import com.estudo.pontointeligente.services.FuncionarioService;
import com.estudo.pontointeligente.utils.DTOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public FuncionarioDTO save(FuncionarioDTO funcioarioDto) {
        log.info("Salvando funcionario: {}", funcioarioDto);
        return DTOUtils.build().funcionarioToDto(funcionarioRepository.save(DTOUtils.build().dtoToFuncionario(funcioarioDto)));
    }

    @Override
    public Optional<FuncionarioDTO> findByCpf(String cpf) {
        log.info("Buscando funcionario pelo cpf {}", cpf);
        return Optional.ofNullable(DTOUtils.build().funcionarioToDto(this.funcionarioRepository.findByCpf(cpf)));
    }

    @Override
    public Optional<FuncionarioDTO> findByEmail(String email) {
        log.info("Buscando funcionario por e-mail {}", email);
        return Optional.ofNullable(DTOUtils.build().funcionarioToDto(this.funcionarioRepository.findByEmail(email)));
    }

    @Override
    public Optional<FuncionarioDTO> findById(Long id) {
        log.info("Buscando funcionario por id {}", id);
        return Optional.ofNullable(DTOUtils.build().funcionarioToDto(this.funcionarioRepository.findById(id).get()));
    }
}
