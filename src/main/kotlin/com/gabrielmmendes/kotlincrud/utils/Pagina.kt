package com.gabrielmmendes.kotlincrud.utils

import lombok.Data

@Data
class Pagina<T>(
    val lista: List<T>,
    val qtdPaginas: Int,
    val totalItens: Int,
    val temProximaPagina: Boolean
)