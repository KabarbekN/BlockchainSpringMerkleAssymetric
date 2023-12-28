package com.example.blockchain1.service;

import com.example.blockchain1.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BlockchainService {
    private final Blockchain blockchain;
    private List<Transaction> transactions;
    private final EncryptionService encryptionService;

    public CreateBlockResponse createNewBlock(){
        Block block = getLatestBlock();
        Integer id = blockchain.getChain().size() - 1;
        Block newBlock = new Block(blockchain.getChain().get(id).getBlockHash(), transactions);
        List<Block> blocks = blockchain.getChain();
        blocks.add(newBlock);
        blockchain.setChain(blocks);

        this.transactions = null;
        return CreateBlockResponse.builder()
                .id(blocks.size() - 1)
                .index(newBlock.getIndex())
                .build();
    }

    public Block getBlockByIdOfChain(Integer id){
        return blockchain.getChain().get(id);
    }
    public Block getBlockByIndex(Integer index){
        return (Block) blockchain.getChain().stream().filter(block -> Objects.equals(block.getIndex(), index));
    }

    public CreateTransactionResponse createNewTransaction(String sender, String recipient, Double amount){
        if (transactions == null){
            transactions = new ArrayList<>();
        }
        Transaction transaction = new Transaction(sender, recipient, amount);
        transactions.add(transaction);

        Double cipher = encryptionService.encryptMessage(transaction.getTransactionId());
        String sign = encryptionService.createDigitalSignature(String.valueOf(transaction.getTransactionId()));
        System.out.println(cipher);
        System.out.println(sign);
        return CreateTransactionResponse.builder()
                .transaction(transaction)
                .cipherSignKey(CipherSignKey.builder()
                        .cipher(cipher)
                        .sign(sign)
                        .build())
                .build();
    }


    public Transaction getTransactionById(Integer transactionId){
        return blockchain.getChain()
                .stream()
                .flatMap(block -> block.getTransactions().stream())
                .filter(transaction -> Objects.equals(transaction.getTransactionId(), transactionId))
                .findFirst()
                .orElse(null);
    }


    public String checkTransactionMessageWithSignature(Double cipher, String sign){
        Integer transactionIdMessage = encryptionService.decryptMessage(cipher);
//        Transaction transaction1 = blockchain.getChain()
//                .stream()
//                .flatMap(block -> block.getTransactions().stream())
//                .filter(transaction -> Objects.equals(transaction.getTransactionId(), transactionIdMessage))
//                .findFirst()
//                .orElse(null);

        Boolean check = encryptionService.verifyDigitalSignature(String.valueOf(transactionIdMessage), sign);
        String result = new String("Transaction id is: " + transactionIdMessage + ", check with signature was " + check);
        return result;
    }

    public Block getLatestBlock(){

        return blockchain.getChain().get(blockchain.getChain().size() - 1);
//        return chain.get(chain.size() - 1);
    }

//    public String[] getTransactionsFromMerkleTree(String merkleRoot){
//        Block block1 = (Block) blockchain.getChain().stream().filter(block -> block.getMerkleTree().equals(merkleRoot));
//        String[] traverse = block1.traverseMerkleTree();
//        return traverse;
//
//    }

//    public String[] getTransactionsFromMerkleTree(String merkleRoot) {
//        Optional<Block> matchingBlock = blockchain.getChain()
//                .stream()
//                .filter(block -> block.getMerkleTree().equals(merkleRoot))
//                .findFirst();
//
//        if (matchingBlock.isPresent()) {
//            return matchingBlock.get().traverseMerkleTree();
//        } else {
//            return new String[0]; // Return an empty array or handle accordingly
//        }
//    }

}
