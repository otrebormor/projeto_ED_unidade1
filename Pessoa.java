package EstruturaDados.TrabalhoPratico;

import java.time.Instant;

public class Pessoa {
    private String nome;
    private int idade;
    private Instant horarioInicio = Instant.now();
    private Instant horarioFinal;

    public Pessoa(String nome, int idade) {
        this.idade = idade;
        this.nome = nome;
    }

    public Instant getHorarioFinal() {
        return horarioFinal;
    }

    public String getNome() {
        return this.nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public Instant getHorarioInicio() {
        return this.horarioInicio;
    }
    public String toString() {
        return "Nome: "+this.nome + " - idade: " + this.idade +" anos - Entrou na fila: "+ this.horarioInicio;
    }
    public void fimAtendimento() {
        this.horarioFinal = Instant.now();
    }

}
