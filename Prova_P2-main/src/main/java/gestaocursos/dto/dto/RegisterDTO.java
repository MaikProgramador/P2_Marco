package com.example.gestaocursos.dto;

// Usamos um "record" do Java, que é uma forma moderna e concisa
// para criar classes que apenas guardam dados.
public record RegisterDTO(String nome, String email, String senha) {
}