package com.example.gestaocursos.security;

import com.example.gestaocursos.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// Esta classe implementa a interface UserDetailsService do Spring Security.
// A responsabilidade dela é única: carregar os dados de um usuário a partir de uma fonte de dados (nosso banco).
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AlunoRepository repository;

    // Este é o método que o Spring Security chama por baixo dos panos durante a autenticação.
    // Ele usa o "username" (que no nosso caso é o email) para buscar o usuário.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usamos o método que criamos no AlunoRepository para buscar pelo email.
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));
    }
}