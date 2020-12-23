package com.estudo.pontointeligente.services;

import com.estudo.pontointeligente.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {

    /**
     *
     * @param cnpj
     * @return Optional<Empresa>
     */
    Optional<Empresa> findByCnpj(String cnpj);

    /**
     *
     * @param empresa
     * @return Empresa
     */
    Empresa save(Empresa empresa);

}
