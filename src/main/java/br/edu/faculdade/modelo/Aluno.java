package br.edu.faculdade.modelo;

import java.time.LocalDate;
import java.time.Period;

public class Aluno {
    private int id;
    private String cpf;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private boolean ativo;
    private Curso curso;

    public Aluno() {
        this.ativo = true;
    }

    public Aluno(String cpf, String nome, String email, LocalDate dataNascimento, Curso curso) {
        this();
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.curso = curso;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public boolean isMaiorDeIdade() {
        return getIdade() >= 16;
    }

    @Override
    public String toString() {
        return nome + " (CPF: " + cpf + ")";
    }
} 