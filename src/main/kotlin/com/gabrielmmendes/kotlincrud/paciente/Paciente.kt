package com.gabrielmmendes.kotlincrud.paciente

import com.gabrielmmendes.kotlincrud.paciente.enums.NivelPrioridade
import com.gabrielmmendes.kotlincrud.paciente.enums.TipoSexo
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import lombok.Data
import java.util.Date

@Entity
@Table(name = "paciente")
@Data
data class Paciente (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do paciente", example = "1", type = "int", required = true)
    val id: Long = 0L,

    @Schema(description = "Nome completo do paciente", example = "João da Silva", required = true)
    val nome: String = "",

    @Schema(description = "CPF do paciente (Cadastro de Pessoa Física)", example = "12345678909", required = true)
    val cpf: String = "",

    @Schema(description = "Data de nascimento do paciente", example = "1980-01-01", type = "string", format = "date", required = true)
    val dataNascimento: Date = Date(),

    @Schema(description = "Nome da mãe do paciente", example = "Maria da Silva", required = true)
    val nomeMae: String = "",

    @Enumerated(EnumType.STRING)
    @Schema(description = "Sexo do paciente", example = "MASCULINO", allowableValues = ["MASCULINO", "FEMININO", "OUTRO"], required = true)
    val sexo: TipoSexo = TipoSexo.OUTRO,

    @Schema(description = "Número do Cartão SUS do paciente", example = "898001234567890", required = false)
    val cartaoSus: String? = null,

    @Schema(description = "Primeiro número de telefone do paciente", example = "(11) 91234-5678", required = true)
    val telefone1: String = "",

    @Schema(description = "Segundo número de telefone do paciente", example = "(11) 99876-5432", required = false)
    val telefone2: String? = null,

    @Schema(description = "Endereço de e-mail do paciente", example = "usuario@dominio.com", required = true)
    val email: String = "",

    @Schema(description = "CEP do endereço do paciente", example = "12345-678", required = true)
    val cep: String = "",

    @Schema(description = "Bairro do endereço do paciente", example = "Centro", required = true)
    val bairro: String = "",

    @Schema(description = "Logradouro do endereço do paciente", example = "Rua das Flores", required = true)
    val logradouro: String = "",

    @Schema(description = "Complemento do endereço do paciente", example = "Apto 101", required = false)
    val complemento: String? = null,

    @Schema(description = "Número do endereço do paciente", example = "123", required = false)
    val numero: Int? = null,

    @Schema(description = "Indica se o paciente é tabagista", example = "false", required = true)
    val tabagista: Boolean = false,

    @Schema(description = "Indica se o paciente é etilista", example = "false", required = true)
    val etilista: Boolean = false,

    @Schema(description = "Indica se há lesão suspeita no paciente", example = "false", required = true)
    val lesaoSuspeita: Boolean = false,

    @Enumerated(EnumType.STRING)
    @Schema(description = "Nível de prioridade do atendimento ao paciente", example = "BAIXA", allowableValues = ["BAIXA", "MEDIA", "ALTA"], required = true)
    val nivelPrioridade: NivelPrioridade = NivelPrioridade.BAIXA

//    val registroLesao: Arquivo? = null,
)