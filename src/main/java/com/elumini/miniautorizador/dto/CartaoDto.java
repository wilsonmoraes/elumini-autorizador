package com.elumini.miniautorizador.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CartaoDto {

    private String numeroCartao;
    private String senha;

}
