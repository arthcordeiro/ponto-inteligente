package com.estudo.pontointeligente.services.impl;

import com.estudo.pontointeligente.dto.EmpresaDTO;
import com.estudo.pontointeligente.respositories.EmpresaRepository;
import com.estudo.pontointeligente.services.EmpresaService;
import com.estudo.pontointeligente.utils.DTOUtils;
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
    public Optional<EmpresaDTO> findByCnpj(String cnpj) {
        log.info("Buscando empresa pelo CNPJ, " +cnpj);
        return Optional.ofNullable(DTOUtils.build().empresaToDto(empresaRepository.findByCnpj(cnpj)));
    }

    @Override
    public EmpresaDTO save(EmpresaDTO empresaDto) {
        log.info("Salvando a empresa "+empresaDto.getRazaoSocial()+" no banco de dados");
        return DTOUtils.build().empresaToDto(this.empresaRepository.save(DTOUtils.build().dtoToempresa(empresaDto)));
    }
}
