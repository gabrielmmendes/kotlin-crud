package com.gabrielmmendes.kotlincrud.paciente

import com.gabrielmmendes.kotlincrud.utils.Pagina
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
class PacienteController(@Autowired private val repository: PacienteRepository) {

    @GetMapping
    fun getAll(@RequestParam(defaultValue = "0") pagina: Int,
               @RequestParam(defaultValue = "10") qtdItens: Int): ResponseEntity<Pagina<Paciente>> {
        val pacientes = repository.findAll(PageRequest.of(pagina, qtdItens))
        return ResponseEntity.ok().body(Pagina(pacientes.content, pacientes.totalPages, pacientes.totalElements.toInt(), pacientes.hasNext()))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Paciente> {
        val paciente = repository.findById(id)
        if (paciente.isPresent) {
            return ResponseEntity.ok(paciente.get())
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun save(@RequestBody paciente: Paciente): ResponseEntity<Paciente> {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(paciente))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Long> {
        repository.deleteById(id)
        return ResponseEntity.ok().body(id)
    }

}
