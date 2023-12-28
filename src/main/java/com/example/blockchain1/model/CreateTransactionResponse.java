package com.example.blockchain1.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionResponse {
    private Transaction transaction;
    private CipherSignKey cipherSignKey;
}
