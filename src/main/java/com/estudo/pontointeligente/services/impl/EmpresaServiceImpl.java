package com.estudo.pontointeligente.services.impl;

import com.estudo.pontointeligente.entities.Empresa;
import com.estudo.pontointeligente.respositories.EmpresaRepository;
import com.estudo.pontointeligente.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> findByCnpj(String cnpj) {
        log.info("Buscando empresa pelo CNPJ: " +cnpj);
        return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
    }

    @Override
    public Empresa save(Empresa empresa) {
        log.info("Salvando a empresa "+empresa.getRazaoSocial()+" no banco de dados");
        return this.empresaRepository.save(empresa);
    }
}
