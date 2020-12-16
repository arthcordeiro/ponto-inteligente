package com.estudo.pontointeligente.services;

import com.estudo.pontointeligente.dto.EmpresaDTO;
import java.util.Optional;

public interface EmpresaService {

    /**
     *
     * @param cnpj
     * @return Optional<Empresa>
     */
    Optional<EmpresaDTO> findByCnpj(String cnpj);

    /**
     *
     * @param empresa
     * @return Empresa
     */
    EmpresaDTO save(EmpresaDTO empresaDto);

}
