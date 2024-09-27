package com.gabrielmmendes.kotlincrud.paciente

import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pacientes")
@AllArgsConstructor
class PacienteController(@Autowired private val pacienteService: PacienteService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<Paciente>> =
        ResponseEntity.ok().body(pacienteService.findAll())
}
