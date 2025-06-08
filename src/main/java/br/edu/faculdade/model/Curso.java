package br.edu.faculdade.model;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private int id;
    private String nome;
    private int cargaHoraria;
    private int vagas;
    private int vagasOcupadas;
    private boolean ativo;
    private List<Aluno> alunos;

    public Curso() {
        this.ativo = true;
        this.vagasOcupadas = 0;
        this.alunos = new ArrayList<>();
    }

    public Curso(String nome, int cargaHoraria, int vagas) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.vagas = vagas;
        this.vagasOcupadas = 0;
        this.ativo = true;
        this.alunos = new ArrayList<>();
    }

    public Curso(int id, String nome, int cargaHoraria, int vagas, int vagasOcupadas, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.vagas = vagas;
        this.vagasOcupadas = vagasOcupadas;
        this.ativo = ativo;
        this.alunos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public int getVagasOcupadas() {
        return vagasOcupadas;
    }

    public void setVagasOcupadas(int vagasOcupadas) {
        this.vagasOcupadas = vagasOcupadas;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public boolean podeAdicionarAluno() {
        return ativo && vagasOcupadas < vagas;
    }

    public void adicionarAluno(Aluno aluno) {
        if (podeAdicionarAluno()) {
            alunos.add(aluno);
            vagasOcupadas++;
        } else {
            throw new IllegalStateException("Não é possível adicionar mais alunos ao curso");
        }
    }

    public void removerAluno(Aluno aluno) {
        if (alunos.remove(aluno)) {
            vagasOcupadas--;
        }
    }

    public void desativar() {
        this.ativo = false;
    }

    @Override
    public String toString() {
        return nome;
    }
} 