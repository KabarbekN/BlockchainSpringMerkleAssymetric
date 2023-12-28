package com.example.blockchain1.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.math.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
@Service
@Getter
@Setter
public class EncryptionService {
    private Integer d;
    private Integer e;
    private Integer prime1 = 251;
    private Integer prime2 = 313;
    private Integer primeMul;
    private Integer primeMul1;

    public EncryptionService() {
//        this.primeMul = prime1 * prime2;
//        this.primeMul1 = (prime1 - 1) * (prime2 - 1);
        findKeys();
    }

    private Integer getGCD(Integer mod, Integer num) {
        if (mod == 0)
            return num;
        else
            return getGCD(num % mod, mod);
    }

    private void findKeys() {
        this.primeMul = prime1 * prime2;
        this.primeMul1 = (prime1 - 1) * (prime2 - 1);

        for (e = 2; e < primeMul1; e++) {
            if (getGCD(e, primeMul1) == 1) {
                break;
            }
        }
        calculatePrivateKey();
        System.out.println("Public key e is = " + e);
        System.out.println("Private key d is : " + d);
    }

    private void calculatePrivateKey() {
        for (int m = 0; m < primeMul1; m++) {
            int temp = 1 + (m * primeMul1);
            if (temp % e == 0) {
                d = temp / e;
                break;
            }
        }
    }

    public double encryptMessage(Integer message) {
        Double cipher = (Math.pow(message, e)) % primeMul;
        System.out.println("Cipher text is : " + cipher);
        return cipher;
    }

    public int decryptMessage(Double cipher) {
        BigInteger bigN = BigInteger.valueOf(primeMul);
        BigInteger bigC = BigDecimal.valueOf(cipher).toBigInteger();
        BigInteger decryptedMessage = (bigC.pow(d)).mod(bigN);
        System.out.println("Decrypted text is : " + decryptedMessage);
        return decryptedMessage.intValue();
    }

    public String createDigitalSignature(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes());
            return new BigInteger(1, hash).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean verifyDigitalSignature(String message, String signature) {
        String newSignature = createDigitalSignature(message);
        return newSignature != null && newSignature.equals(signature);
    }
}
