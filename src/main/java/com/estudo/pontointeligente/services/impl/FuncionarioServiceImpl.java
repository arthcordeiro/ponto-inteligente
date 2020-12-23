package com.estudo.pontointeligente.services.impl;

import com.estudo.pontointeligente.entities.Funcionario;
import com.estudo.pontointeligente.respositories.FuncionarioRepository;
import com.estudo.pontointeligente.services.FuncionarioService;
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
    public Funcionario save(Funcionario funcionario) {
        log.info("Salvando funcionario: {}", funcionario);
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> findByCpf(String cpf) {
        log.info("Buscando funcionario pelo cpf {}", cpf);
        return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Funcionario> findByEmail(String email) {
        log.info("Buscando funcionario por e-mail {}", email);
        return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
    }

    @Override
    public Optional<Funcionario> findById(Long id) {
        log.info("Buscando funcionario por id {}", id);
        return Optional.ofNullable(this.funcionarioRepository.findById(id).get());
    }
}
