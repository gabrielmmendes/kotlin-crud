package com.gabrielmmendes.kotlincrud.paciente

import com.gabrielmmendes.kotlincrud.utils.Pagina
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PacienteService(@Autowired private val repository: PacienteRepository) {

    fun findAll(pagina: Int, qtdItens: Int): Pagina<Paciente> {
        val pacientes = repository.findAll(PageRequest.of(pagina, qtdItens))

        return Pagina(pacientes.content, pacientes.totalPages, pacientes.totalElements.toInt(), pacientes.hasNext())
    }

    fun save(paciente: Paciente): Paciente {
        return repository.save(paciente)
    }
}