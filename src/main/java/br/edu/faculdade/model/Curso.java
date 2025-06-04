package br.edu.faculdade.model;

public class Curso {
    private int id;
    private String nome;
    private int cargaHoraria;
    private int vagas;
    private int vagasOcupadas;
    private boolean ativo;

    public Curso() {
        this.ativo = true;
        this.vagasOcupadas = 0;
    }

    public Curso(String nome, int cargaHoraria, int vagas) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.vagas = vagas;
        this.vagasOcupadas = 0;
        this.ativo = true;
    }

    public Curso(int id, String nome, int cargaHoraria, int vagas, int vagasOcupadas, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.vagas = vagas;
        this.vagasOcupadas = vagasOcupadas;
        this.ativo = ativo;
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

    public boolean podeAdicionarAluno() {
        return ativo && vagasOcupadas < vagas;
    }

    public void adicionarAluno() {
        if (podeAdicionarAluno()) {
            vagasOcupadas++;
        }
    }

    public void removerAluno() {
        if (vagasOcupadas > 0) {
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