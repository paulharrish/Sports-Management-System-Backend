package com.project.sportsManagement.utils;

import java.security.KeyPairGenerator;

import java.security.KeyPair;

public class KeyGenerator {
        public static KeyPair generateRsaKey() {

            KeyPair keyPair;

            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048);
                keyPair = keyPairGenerator.generateKeyPair();

            } catch (Exception e) {
                throw new IllegalStateException();
            }
            return keyPair;
        }
    }

