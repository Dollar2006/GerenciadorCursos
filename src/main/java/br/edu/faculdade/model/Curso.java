package br.edu.faculdade.model;

public class Curso {
    private String nome;
    private int cargaHoraria;
    private int vagas;
    private int vagasOcupadas;
    private boolean ativo;

    public Curso(String nome, int cargaHoraria, int vagas) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.vagas = vagas;
        this.vagasOcupadas = 0;
        this.ativo = true;
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
        return "Curso{" +
                "nome='" + nome + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", vagas=" + vagas +
                ", vagasOcupadas=" + vagasOcupadas +
                ", ativo=" + ativo +
                '}';
    }
} 