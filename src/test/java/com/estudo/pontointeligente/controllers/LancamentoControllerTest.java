package com.estudo.pontointeligente.controllers;

import com.estudo.pontointeligente.dto.LancamentoDTO;
import com.estudo.pontointeligente.entities.Funcionario;
import com.estudo.pontointeligente.entities.Lancamento;
import com.estudo.pontointeligente.enums.TipoEnum;
import com.estudo.pontointeligente.services.FuncionarioService;
import com.estudo.pontointeligente.services.LancamentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LancamentoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LancamentoService lancamentoService;

    @MockBean
    private FuncionarioService funcionarioService;

    private final static String URL_BASE = "/api/lancamentos/";
    private final static Long ID_FUNCIONARIO = 1L;
    private static final Long ID_LANCAMENTO = 1L;
    private static final String TIPO = TipoEnum.INICIO_TRABALHO.name();
    private static final Date DATA = new Date();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testCadastrarLancamento() throws Exception {
        Lancamento lancamento = this.obterDadosLancamento();
        BDDMockito.given(this.funcionarioService.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.lancamentoService.save(Mockito.any(Lancamento.class))).willReturn(lancamento);

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID_LANCAMENTO))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.tipo").value(TIPO))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void testCadastrarLancamentoFuncionarioIdInvalido() throws Exception {
        BDDMockito.given(this.funcionarioService.findById(Mockito.anyLong())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Funcionário não encontrado. ID Inexistente."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void testRemoverLancamento() throws Exception {
        BDDMockito.given(this.lancamentoService.findById(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

        mvc.perform(MockMvcRequestBuilders.delete(URL_BASE+ID_LANCAMENTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser
    public void testRemoverLancamentoAcessoNegado() throws Exception {
        BDDMockito.given(this.lancamentoService.findById(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

        mvc.perform(MockMvcRequestBuilders.delete(URL_BASE+ID_LANCAMENTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    private String obterJsonRequisicaoPost() throws JsonProcessingException {
        LancamentoDTO lancamentoDTO = new LancamentoDTO();
        lancamentoDTO.setId(null);
        lancamentoDTO.setData(this.dateFormat.format(DATA));
        lancamentoDTO.setTipo(TIPO);
        lancamentoDTO.setFuncionarioId(ID_FUNCIONARIO);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(lancamentoDTO);
    }

    private Lancamento obterDadosLancamento() {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(ID_LANCAMENTO);
        lancamento.setDataLancamento(DATA);
        lancamento.setTipo(TipoEnum.valueOf(TIPO));
        lancamento.setFuncionario(new Funcionario());
        lancamento.getFuncionario().setId(ID_FUNCIONARIO);

        return lancamento;
    }
}
