package com.example.blockchain1.model;

import lombok.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




@Getter
@Setter
public class Block {
    private static Integer id = 0;
    private Integer index;
    private Integer previousHash;
    private List<Transaction> transactions;
    private String merkleTree;
    private Integer blockHash;

    public Block(Integer previousHash, List<Transaction> transactions) {
        this.index = id++;
        this.previousHash = previousHash;
        this.transactions = transactions;
        calculateBlockHash();
    }
    public void calculateBlockHash(){
        String merkleRoot = buildMerkleTree(transactions);
        this.merkleTree = merkleRoot;
        Object[] contents = {merkleRoot, previousHash};
        this.blockHash = Arrays.hashCode(contents);
    }


    private String buildMerkleTree(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return "";
        }

        List<String> currentLevel = new ArrayList<>();
        for (Transaction transaction : transactions) {
            currentLevel.add(transaction.getTransactionId().toString());
        } // current level filling by id of transaction and casting to string
        // 1, 2, 3, 4, 5, 6, 7, 8, ...

        //    h(1,2,3,4,5,6,7,8)
        //  h(1,2,3,4)  h(5,6,7,8)
        // h(1,2) h(3,4) h(5,6) h(7,8)
        // 1, 2, 3, 4, 5, 6, 7, 8, ... should be like this

        while (currentLevel.size() > 1) {
            List<String> nextLevel = new ArrayList<>();

            for (int i = 0; i < currentLevel.size(); i += 2) {
                String left = currentLevel.get(i);
                String right = (i + 1 < currentLevel.size()) ? currentLevel.get(i + 1) : "";
                String combinedHash = calculateHash(left + right);
                nextLevel.add(combinedHash);
            }

            currentLevel = nextLevel;
        }

        //              1(h)
        //          2(h)    3(h)
        //       4(h) 5(h) 6(h) 7(h)
        //      8(h)
        //

        return currentLevel.get(0);
    }

    private String calculateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes());

            // Convert byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
//        return data; // Placeholder for hash calculation
    }
//    public String[] traverseMerkleTree() {
//        System.out.println("Merkle Tree:");
//
//        String[] merkleTreeNodes = merkleTree.split(",");
////        for (String node : merkleTreeNodes) {
////            System.out.println(node.trim());
////        }
//
//        return merkleTreeNodes;
//    }

}
