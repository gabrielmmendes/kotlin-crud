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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
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
            .andExpect(jsonPath("lista.length()").value(greaterThan(0)))
            .andReturn()
    }

    @Test
    fun listarPacientes_whenGetRequest_thenReturnPacienteList() {
        mockMvc.perform(get("/pacientes")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.lista").isArray)
            .andExpect(jsonPath("$.lista[?(@.nome == 'João Silva')]").exists())
            .andExpect(jsonPath("$.lista[?(@.cpf == '12345678901')]").exists())
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

    @Test
    fun cadastrarPaciente_thenReturnSavedPaciente() {
        val paciente = Paciente(
          nome= "Carlos Pereira",
          cpf= "32145698700",
          dataNascimento= Date(),
          nomeMae= "Mariana Pereira",
          sexo= TipoSexo.MASCULINO ,
          telefone1= "11998765432",
          email= "carlos.pereira@email.com",
          cep= "87654321",
          bairro= "Zona Sul",
          logradouro= "Avenida Paulista",
          numero= 200,
          tabagista= false,
          etilista= true,
          lesaoSuspeita= false,
          nivelPrioridade= NivelPrioridade.BAIXA
        )

        mockMvc.perform(post("/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(paciente))
                .characterEncoding(Charsets.UTF_8))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.nome").value("Carlos Pereira"))
            .andExpect(jsonPath("$.cpf").value("32145698700"))
            .andExpect(jsonPath("$.telefone1").value("11998765432"))
            .andExpect(jsonPath("$.email").value("carlos.pereira@email.com"))
            .andExpect(jsonPath("$.cep").value("87654321"))
            .andReturn()
    }

    @Test
    fun `quando deletar um paciente com id valido, deve retornar status 204`() {
        // Supondo que o paciente com ID 1 existe
        mockMvc.perform(delete("/pacientes/{id}", 1))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
    }

    @Test
    fun `quando deletar sem fornecer id, deve retornar status 400`() {
        mockMvc.perform(delete("/pacientes/"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNotFound)  // Verifica se retorna 405 (Method Not Allowed)
    }

    @Test
    fun `quando deletar com id invalido, deve retornar status 400`() {
        mockMvc.perform(delete("/pacientes/{id}", "abc"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isBadRequest)  // Verifica se retorna 400 (Bad Request)
    }
}
