package com.gabrielmmendes.kotlincrud.paciente

import com.gabrielmmendes.kotlincrud.utils.Pagina
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/pacientes")
@AllArgsConstructor
@Tag(name = "PacienteController", description = "Controller com operações CRUD de Paciente")
class PacienteController(@Autowired private val repository: PacienteRepository) {

    @GetMapping
    @Operation(
        summary = "Retorna a lista de pacientes paginada",
        description = "Busca todos os pacientes de forma paginada, com a possibilidade de ajustar o número de itens por página.",
        parameters = [
            Parameter(name = "pagina", description = "Número da página a ser exibida", example = "0", required = false),
            Parameter(name = "qtdItens", description = "Quantidade de itens por página", example = "10", required = false)
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso"),
            ApiResponse(responseCode = "400", description = "Parâmetros de requisição inválidos")
        ]
    )
    fun getAll(
        @RequestParam(defaultValue = "0") pagina: Int,
        @RequestParam(defaultValue = "10") qtdItens: Int
    ): ResponseEntity<Pagina<Paciente>> {
        val pacientes = repository.findAll(PageRequest.of(pagina, qtdItens))
        return ResponseEntity.ok().body(Pagina(pacientes.content, pacientes.totalPages, pacientes.totalElements.toInt(), pacientes.hasNext()))
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Retorna um paciente pelo ID",
        description = "Busca um paciente específico pelo seu identificador único.",
        parameters = [
            Parameter(name = "id", description = "Identificador único do paciente", example = "1", required = true)
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Paciente não encontrado")
        ]
    )
    fun getById(@PathVariable id: Long): ResponseEntity<Paciente> {
        val paciente = repository.findById(id)
        if (paciente.isPresent) {
            return ResponseEntity.ok(paciente.get())
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    @Operation(
        summary = "Cria um novo paciente",
        description = "Cria um novo registro de paciente no banco de dados.",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do paciente a ser criado",
            required = true
        ),
        responses = [
            ApiResponse(responseCode = "201", description = "Paciente criado com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados inválidos para criação do paciente")
        ]
    )
    fun save(@RequestBody paciente: Paciente): ResponseEntity<Paciente> {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(paciente))
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar um paciente",
        description = "Atualiza as informações de um paciente pelo seu ID",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do paciente a ser criado",
            required = true
        )
    )
    fun update(@PathVariable id: Long,
               @RequestBody paciente: Paciente): ResponseEntity<Paciente> {
        paciente.id = id
        return ResponseEntity.ok().body(repository.save(paciente))
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Exclui um paciente pelo ID",
        description = "Remove um paciente do banco de dados com base no seu identificador.",
        parameters = [
            Parameter(name = "id", description = "Identificador único do paciente a ser removido", example = "1", required = true)
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Paciente excluído com sucesso"),
            ApiResponse(responseCode = "404", description = "Paciente não encontrado para exclusão")
        ]
    )
    fun delete(@PathVariable id: Long): ResponseEntity<Long> {
        repository.deleteById(id)
        return ResponseEntity.ok().body(id)
    }
}
