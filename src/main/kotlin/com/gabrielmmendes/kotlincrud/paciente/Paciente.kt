package com.gabrielmmendes.kotlincrud.paciente

import com.gabrielmmendes.kotlincrud.paciente.enums.NivelPrioridade
import com.gabrielmmendes.kotlincrud.paciente.enums.TipoSexo
import jakarta.persistence.*
import lombok.Data
import java.util.Date

@Entity
@Table(name = "paciente")
@Data
data class Paciente (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val nome: String = "",
    val cpf: String = "",
    val dataNascimento: Date = Date(),
    val nomeMae: String = "",
    @Enumerated(EnumType.STRING)
    val sexo: TipoSexo = TipoSexo.OUTRO,
    val cartaoSus: String? = null,
    val telefone1: String = "",
    val telefone2: String? = null,
    val email: String = "",
    val cep: String = "",
    val bairro: String = "",
    val logradouro: String = "",
    val complemento: String? = null,
    val numero: Int? = null,
    val tabagista: Boolean = false,
    val etilista: Boolean = false,
    val lesaoSuspeita: Boolean = false,
    @Enumerated(EnumType.STRING)
    val nivelPrioridade: NivelPrioridade = NivelPrioridade.BAIXA
//    val registroLesao: Arquivo? = null,
)