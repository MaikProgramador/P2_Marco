package com.example.gestaocursos.services;

import com.example.gestaocursos.dto.LoginDTO;
import com.example.gestaocursos.dto.RegisterDTO;
import com.example.gestaocursos.models.Aluno;
import com.example.gestaocursos.repositories.AlunoRepository;
import com.example.gestaocursos.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Este método será usado pelo Spring Security para carregar o usuário pelo email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }

    // Lógica para registrar um novo usuário
    public void register(RegisterDTO data) {
        if (this.repository.findByEmail(data.email()).isPresent()) {
            throw new RuntimeException("Um usuário com este email já existe.");
        }

        String encryptedPassword = passwordEncoder.encode(data.senha());
        Aluno newAluno = new Aluno(data.nome(), data.email(), encryptedPassword);

        this.repository.save(newAluno);
    }
    
    // Lógica para realizar o login
    public String login(LoginDTO data) {
        // O Spring Security usa este objeto para agrupar o email e a senha
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

        // O AuthenticationManager irá validar a senha e o usuário
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Se a autenticação for bem-sucedida, geramos o token
        return tokenService.generateToken((Aluno) auth.getPrincipal());
    }
}