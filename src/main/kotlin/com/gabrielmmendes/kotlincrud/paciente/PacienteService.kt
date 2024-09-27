package com.gabrielmmendes.kotlincrud.paciente

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PacienteService(@Autowired private val repository: PacienteRepository) {

    fun findAll(): List<Paciente> {
        return repository.findAll()
    }
}