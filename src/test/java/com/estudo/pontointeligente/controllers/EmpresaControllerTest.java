package com.estudo.pontointeligente.controllers;

import com.estudo.pontointeligente.entities.Empresa;
import com.estudo.pontointeligente.services.EmpresaService;
import jdk.net.SocketFlow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmpresaService empresaService;

    private static final String BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
    private static final Long ID = Long.valueOf(2);
    private static final String CNPJ = "87102252000100";
    private static final String RAZAO_SOCIAL = "Empresa XPTO";

    @Test
    @WithMockUser
    public void testBuscarEmpresaCNPJInvalido() throws Exception {
        BDDMockito.given(this.empresaService.findByCnpj(Mockito.anyString())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Empresa não encontrada para o CNPJ " + CNPJ));
    }

    @Test
    @WithMockUser
    public void testBuscarEmpresaCNPJValido() throws Exception {
        BDDMockito.given(this.empresaService.findByCnpj(Mockito.anyString())).willReturn(Optional.of(this.obterDadosEmpresa()));

        mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL+CNPJ)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.razao_social").value(RAZAO_SOCIAL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cnpj").value(CNPJ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty());

    }

    private Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setCnpj(CNPJ);
        empresa.setRazaoSocial(RAZAO_SOCIAL);
        empresa.setId(ID);

        return empresa;
    }
}
