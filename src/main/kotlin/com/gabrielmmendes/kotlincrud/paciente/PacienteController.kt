package com.gabrielmmendes.kotlincrud.paciente

import com.gabrielmmendes.kotlincrud.utils.Pagina
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pacientes")
@AllArgsConstructor
class PacienteController(@Autowired private val pacienteService: PacienteService) {

    @GetMapping
    fun getAll(@RequestParam(defaultValue = "0") pagina: Int,
               @RequestParam(defaultValue = "10") qtdItens: Int): ResponseEntity<Pagina<Paciente>> {
        val pacientes = pacienteService.findAll(pagina, qtdItens)
        return ResponseEntity.ok().body(pacientes)
    }

    @PostMapping
    fun save(@RequestBody paciente: Paciente): ResponseEntity<Paciente> {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.save(paciente))
    }
}
