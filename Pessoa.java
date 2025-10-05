package EstruturaDados.TrabalhoPratico;

import java.time.Duration;
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


    public long getDuracaoAtendimentoSegundos() {
        if (horarioFinal != null && horarioInicio != null) {
            return Duration.between(horarioInicio, horarioFinal).getSeconds();
        }
        return -1; // ou outro valor para indicar que não terminou
    }

    public String toString() {
        return "Nome: "+this.nome + " - idade: " + this.idade +" anos - Entrou na fila: "+ this.horarioInicio + " - Atendido em: " + (horarioFinal != null ? horarioFinal : "Ainda não atendido") + (horarioFinal != null ? " - Duração do atendimento: " + getDuracaoAtendimentoSegundos() + " segundos" : "");
    }
    public void fimAtendimento() {
        this.horarioFinal = Instant.now();
    }

}
