package com.example.blockchain1.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlockResponse {
    private Integer id;
    private Integer index;
}
