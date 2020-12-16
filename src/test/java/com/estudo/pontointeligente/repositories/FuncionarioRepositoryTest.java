package com.estudo.pontointeligente.repositories;

import com.estudo.pontointeligente.entities.Empresa;
import com.estudo.pontointeligente.entities.Funcionario;
import com.estudo.pontointeligente.enums.PerfilEnum;
import com.estudo.pontointeligente.respositories.EmpresaRepository;
import com.estudo.pontointeligente.respositories.FuncionarioRepository;
import com.estudo.pontointeligente.utils.PasswordUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String EMAIL = "email@email.com.br";
    private static final String CPF = "81184475024";

    @Before
    public void setup() throws Exception {
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        this.funcionarioRepository.save(obterDadosFuncioario(empresa));
    }

    @After
    public final void tearDown() {
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testeBuscarFuncionarioEmail() {
        Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
        Assert.assertEquals(EMAIL, funcionario.getEmail());
    }

    @Test
    public void testeBuscarFuncionarioCpf() {
        Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
        Assert.assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    public void testeBuscarFuncionarioCpfOrEmail() {
        Funcionario funcionario = funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
        Assert.assertNotNull(funcionario);
    }

    @Test
    public void testeBuscarFuncionarioCpfOrEmailInvalido() {
        Funcionario funcionario = funcionarioRepository.findByCpfOrEmail(CPF, "email@invalido.com");
        Assert.assertNotNull(funcionario);
    }

    @Test
    public void testeBuscarFuncionarioCpfInvalidoOrEmail() {
        Funcionario funcionario = funcionarioRepository.findByCpfOrEmail("12345678910", EMAIL);
        Assert.assertNotNull(funcionario);
    }

    private Funcionario obterDadosFuncioario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF);
        funcionario.setName("Fulano");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarSenhaCriptografada("12345abc"));
        funcionario.setEmail(EMAIL);
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

    private Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Exemplo SA");
        empresa.setCnpj("51463645000100");
        return empresa;
    }
}
