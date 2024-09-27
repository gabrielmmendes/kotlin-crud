package com.gabrielmmendes.kotlincrud.paciente

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PacienteRepository : JpaRepository<Paciente, Long>