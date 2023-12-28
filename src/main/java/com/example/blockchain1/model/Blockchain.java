package com.example.blockchain1.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
        generateGenesisBlock();
    }

    public void generateGenesisBlock() {
       Block block = new Block(0, new ArrayList<>());
       chain.add(block);
    }

    public List<Block> getChain() {
        return chain;
    }

    public void setChain(List<Block> chain) {
        this.chain = chain;
    }
}
