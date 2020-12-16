package com.estudo.pontointeligente.respositories;

import com.estudo.pontointeligente.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Transactional
    Empresa findByCnpj(String cnpj);
}
