package com.example.blockchain1.controller;


import com.example.blockchain1.model.CreateBlockResponse;
import com.example.blockchain1.model.CreateTransactionResponse;
import com.example.blockchain1.model.Transaction;
import com.example.blockchain1.service.BlockchainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blockchain")
public class BlockchainController {
    private final BlockchainService blockchainService;

    @GetMapping("/block/add")
    public ResponseEntity<?> createNewBlock(){
        CreateBlockResponse createBlockResponse = blockchainService.createNewBlock();
        return ResponseEntity.accepted().body(createBlockResponse);
    }

    @GetMapping("/block/id/{id}")
    public ResponseEntity<?> getBlockById(@PathVariable Integer id){
        return ResponseEntity.ok(blockchainService.getBlockByIdOfChain(id));
    }
    @GetMapping("/block/index/{index}")
    public ResponseEntity<?> getBlockByIndex(@PathVariable Integer index){
        return ResponseEntity.ok(blockchainService.getBlockByIndex(index));
    }

    @GetMapping("/transaction/add")
    public ResponseEntity<?> createNewTransaction(@RequestParam("sender") String sender,
                                                  @RequestParam("recipient") String recipient,
                                                  @RequestParam("amount") Double amount){
        CreateTransactionResponse createTransactionResponse = blockchainService.createNewTransaction(sender, recipient, amount);
        return ResponseEntity.accepted().body(createTransactionResponse);
    }
    @GetMapping("/transaction/check")
    public ResponseEntity<?> checkTransaction(@RequestParam("cipher") Double cipher,
                                              @RequestParam("sign") String sign){
        return ResponseEntity.ok(blockchainService.checkTransactionMessageWithSignature(cipher, sign));}

    @GetMapping("/transaction/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Integer id){
        return ResponseEntity.ok(blockchainService.getTransactionById(id));

    }

//    @GetMapping("/block/tree/{root}")
//    public ResponseEntity<?> getMerkleTreeByBlockId(@PathVariable String root){
//        return ResponseEntity.ok(blockchainService.getTransactionsFromMerkleTree(root));
//    }
}
