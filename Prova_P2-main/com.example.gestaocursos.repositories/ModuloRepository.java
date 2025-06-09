package com.example.gestaocursos.repositories;

import com.example.gestaocursos.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// A interface estende JpaRepository<Entidade, TipoDaChavePrimaria>
public interface AlunoRepository extends JpaRepository<Modulo, Long> {

    // Spring Data JPA cria a query automaticamente a partir do nome do método
    Optional<Aluno> findByEmail(String email);
}