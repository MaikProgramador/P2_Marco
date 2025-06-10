package com.example.gestaocursos.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.gestaocursos.models.Aluno;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    // Esta anotação pega o valor da nossa chave secreta do application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para o aluno fornecido.
     * @param aluno O aluno para quem o token será gerado.
     * @return O token JWT como uma String.
     */
    public String generateToken(Aluno aluno) {
        try {
            // Define o algoritmo de assinatura usando nossa chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("gestao-cursos-api") // Identifica quem emitiu o token
                    .withSubject(aluno.getEmail())    // O "assunto" do token, geralmente o login do usuário
                    .withExpiresAt(getExpirationDate()) // Define a data de expiração
                    .sign(algorithm); // Assina o token com o algoritmo

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    /**
     * Valida um token JWT e retorna o "assunto" (email do usuário) se for válido.
     * @param token O token JWT a ser validado.
     * @return O email do usuário se o token for válido; uma String vazia caso contrário.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("gestao-cursos-api") // Verifica se o emissor é o mesmo
                    .build()
                    .verify(token) 
                    .getSubject(); 
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}