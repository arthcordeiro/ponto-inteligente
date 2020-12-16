package com.estudo.pontointeligente.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sun.rmi.runtime.Log;

public class PasswordUtils {

    private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    /**
     * Gera um hash utilizando criptografia
     *
     * @param senha
     * @return String
     */
    public static String gerarSenhaCriptografada(String senha) {
        if (senha == null) {
            return senha;
        }

        log.info("Encriptando senha com BCrypt");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(senha);
        log.info("Encoded password "+hash);
        return hash;
    }
}
