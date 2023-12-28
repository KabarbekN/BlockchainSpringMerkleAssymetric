package com.example.blockchain1.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Transaction {
    private static Integer id = 0;
    private Integer transactionId;
    private String sender;
    private String recipient;
    private Double amount;

    public Transaction(String sender, String recipient, Double amount) {
        this.transactionId = id++;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }
}
