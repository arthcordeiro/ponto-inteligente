package com.estudo.pontointeligente.repositories;

import com.estudo.pontointeligente.entities.Empresa;
import com.estudo.pontointeligente.respositories.EmpresaRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CNPJ = "51463645000100";

    @Before
    public void setup() throws Exception {
        Empresa empresa = new Empresa();
        empresa.setCnpj(CNPJ);
        empresa.setRazaoSocial("Exemplo SA");
        this.empresaRepository.save(empresa);
    }

    @After
    public final void tearDown() {
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarPorCnpj() {
        Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
        Assert.assertEquals(CNPJ, empresa.getCnpj());
    }
}
