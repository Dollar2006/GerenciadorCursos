package br.edu.faculdade.model;

import java.time.LocalDate;
import java.time.Period;

public class Aluno {
    private int id;
    private String matricula;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private Curso curso;
    private boolean ativo;

    public Aluno() {
        this.ativo = true;
    }

    public Aluno(String matricula, String cpf, String nome, String email, String telefone, LocalDate dataNascimento, Curso curso) {
        this.matricula = matricula;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.curso = curso;
        this.ativo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        if (this.curso != null) {
            this.curso.removerAluno(this);
        }
        this.curso = curso;
        if (curso != null) {
            curso.adicionarAluno(this);
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public boolean isMaiorDeIdade() {
        return getIdade() >= 16;
    }

    public void desativar() {
        this.ativo = false;
        if (this.curso != null) {
            this.curso.removerAluno(this);
            this.curso = null;
        }
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "matricula='" + matricula + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", curso=" + (curso != null ? curso.getNome() : "Nenhum") +
                ", ativo=" + ativo +
                '}';
    }
} 