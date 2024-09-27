package com.gabrielmmendes.kotlincrud.paciente

import com.fasterxml.jackson.databind.ObjectMapper
import com.gabrielmmendes.kotlincrud.paciente.enums.NivelPrioridade
import com.gabrielmmendes.kotlincrud.paciente.enums.TipoSexo
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class PacienteControllerTest(@Autowired val mockMvc: MockMvc) {

    var mapper = ObjectMapper()

    @Autowired
    lateinit var pacienteRepository: PacienteRepository

    @BeforeAll
    fun setup() {
        val paciente1 = Paciente(
            nome = "João Silva",
            cpf = "12345678901",
            dataNascimento = Date(),
            nomeMae = "Maria Silva",
            sexo = TipoSexo.MASCULINO,
            cartaoSus = "123456789",
            telefone1 = "11987654321",
            telefone2 = "1123456789",
            email = "joao.silva@email.com",
            cep = "12345678",
            bairro = "Centro",
            logradouro = "Rua das Flores",
            complemento = "Apto 12",
            numero = 123,
            tabagista = true,
            etilista = false,
            lesaoSuspeita = false,
            nivelPrioridade = NivelPrioridade.MEDIA
        )

        val paciente2 = Paciente(
            nome = "Ana Souza",
            cpf = "98765432100",
            dataNascimento = Date(),
            nomeMae = "Clara Souza",
            sexo = TipoSexo.FEMININO,
            telefone1 = "11999887766",
            tabagista = false,
            etilista = true,
            lesaoSuspeita = true
        )

        pacienteRepository.save(paciente1)
        pacienteRepository.save(paciente2)
    }

    @Test
    fun findPacienteList_whenGetResquest_thenReturnStatus200() {
        mockMvc.perform(get("/pacientes")
                .characterEncoding(Charsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("lista").isArray())
            .andExpect(jsonPath("lista.length()").value(greaterThan(1)))
            .andReturn()
    }

    @Test
    fun cadastrarPaciente_whenPostRequest_thenReturnStatus201() {
        val paciente = Paciente(
            nome = "João Silva",
            cpf = "12345678901",
            dataNascimento = Date(),
            nomeMae = "Maria Silva",
            sexo = TipoSexo.MASCULINO,
            cartaoSus = "123456789",
            telefone1 = "11987654321",
            telefone2 = "1123456789",
            email = "joao.silva@email.com",
            cep = "12345678",
            bairro = "Centro",
            logradouro = "Rua das Flores",
            complemento = "Apto 12",
            numero = 123,
            tabagista = true,
            etilista = false,
            lesaoSuspeita = false,
            nivelPrioridade = NivelPrioridade.MEDIA
        )

        mockMvc.perform(post("/pacientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(paciente))
            .characterEncoding(Charsets.UTF_8))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated)  // Verifica se o status é 201
            .andExpect(jsonPath("$.nome").value("João Silva"))
            .andExpect(jsonPath("$.cpf").value("12345678901"))
            .andReturn()
    }

}