package com.example.blockchain1.controller;

import com.example.blockchain1.service.EncryptionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EncryptionController {
    private final EncryptionService encryptionService;


    @GetMapping("/cal/{message}")
    public ResponseEntity<?> getEncryptionResult(@PathVariable Integer message){
//        Integer message = 341;
        Double cipher = encryptionService.encryptMessage(message);
        System.out.println("Final result encrypted " + cipher);
        Integer decryptedMessage = encryptionService.decryptMessage(cipher);
        System.out.println("Final result decrypted " + decryptedMessage);
        String sign = encryptionService.createDigitalSignature(String.valueOf(32));
        System.out.println(sign);

        System.out.println(encryptionService.verifyDigitalSignature(
//                String.valueOf(message),
                "31",
                sign));




        return ResponseEntity.accepted().body("result in the terminal" + decryptedMessage);

    }

}
