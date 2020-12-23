package com.estudo.pontointeligente.services;

import com.estudo.pontointeligente.entities.Lancamento;
import com.estudo.pontointeligente.respositories.LancamentoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @MockBean
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(this.lancamentoRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
                .willReturn(new PageImpl<Lancamento>(new ArrayList<Lancamento>()));
        BDDMockito.given(this.lancamentoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));
        BDDMockito.given(this.lancamentoRepository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());
    }

    @Test
    public void testFindLancamentoByFuncionarioId() {
        Page<Lancamento> lancamento = this.lancamentoService.findByFuncionarioId(1L, PageRequest.of(0, 10));
        Assert.assertNotNull(lancamento);
    }

    @Test
    public void testFindLancamentoById() {
        Optional<Lancamento> lancamento = this.lancamentoService.findById(1l);
        Assert.assertNotNull(lancamento);
    }

    @Test
    public void testSalvarLancaento() {
        Lancamento lancamento = this.lancamentoService.save(new Lancamento());
        Assert.assertNotNull(lancamento);
    }
}
