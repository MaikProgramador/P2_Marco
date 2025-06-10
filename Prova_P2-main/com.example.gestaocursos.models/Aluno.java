package com.example.gestaocursos.models; 

import jakarta.persistence.*; 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Aluno implements UserDetails { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "matricula",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private Set<Curso> cursos = new HashSet<>();
    
    // PONTO 2: O JPA precisa de um construtor vazio.
    public Aluno() {
    }

    // O construtor que você adicionou, que usaremos no nosso service.
    public Aluno(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters (pode ser gerado pela sua IDE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public Set<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }


    // MÉTODOS OBRIGATÓRIOS DO USERDETAILS (para o Spring Security)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Não estamos usando perfis (Roles) por enquanto
    }

    @Override
    public String getPassword() {
        return this.senha; // O Spring Security usará este método para pegar a senha
    }

    @Override
    public String getUsername() {
        return this.email; // O Spring Security usará o email como nome de usuário
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}