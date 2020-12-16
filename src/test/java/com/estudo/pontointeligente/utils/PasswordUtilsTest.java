package com.estudo.pontointeligente.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilsTest {

    private static final String SENHA = "123456";
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    @Test
    public void testSenhaNula() throws Exception {
        Assert.assertNull(PasswordUtils.gerarSenhaCriptografada(null));
    }

    @Test
    public void testGerarHashSenha() throws Exception {
        String hash = PasswordUtils.gerarSenhaCriptografada(SENHA);
        Assert.assertTrue(bCryptEncoder.matches(hash, SENHA));
    }
}
