package com.williams.springsecuritycifrado;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class EncryptionTest {

    /**
     * BCrypt genera su propio salt de 16 bytes
     *
     * El resultado de cifrar con bcrypt será un string de 60 caracteres:
     *
     * $a versión
     * $10 fuerza (valor que va de 4 a 31, por defecto vale 10)
     * Los 22 siguientes caracteres son el salt generado
     */
    @Test
    void bcrypTest() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("admin");
        System.out.println(hashedPassword);

        boolean matches = passwordEncoder.matches("admin", hashedPassword);

        System.out.println(matches);
    }

    @Test
    void bcryptCheckMultiplePasswords() {
        for (int i = 0; i < 30; i++) {
            System.out.println(new BCryptPasswordEncoder().encode("admin"));
        }
    }

    @Test
    void springPasswordEncoders() {

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        // encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        // encoders.put("argon2", new Argon2PasswordEncoder());
        // encoders.put("scrypt", new SCryptPasswordEncoder());


        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);

        String hashedPassword = passwordEncoder.encode("admin");
        System.out.println(hashedPassword);
    }
}
