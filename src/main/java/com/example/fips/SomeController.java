package com.example.fips;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping("fips")
public class SomeController {
    @GetMapping
    public Iterable<String> get() {
        return List.of("Hello", " FIPS", " World");
    }

    @GetMapping("/init-key-pair/{keySize}")
    public String initKeyPair(@PathVariable Integer keySize) {
        try {
            prepareKeyPairGenerator(keySize);
            return "Key pair generated successfully! Key size: " + keySize;
        } catch (Exception ex) {
            PrintWriter pw = new PrintWriter(new StringWriter());
            ex.printStackTrace();
            return pw.toString();
        } catch (Error err) {
            PrintWriter pw = new PrintWriter(new StringWriter());
            err.printStackTrace();
            return pw.toString();
        }
    }

    KeyPairGenerator prepareKeyPairGenerator(Integer keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BCFIPS");
        SecureRandom random = SecureRandom.getInstance("DEFAULT", "BCFIPS");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator;
    }
}
