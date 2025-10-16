package EstruturaDados.TrabalhoPratico;

import java.time.Duration;
import java.time.Instant;

public class Pessoa {
    private String nome;
    private int idade;
    private Instant horarioInicio;
    private Instant horarioFinal;

    public Pessoa(String nome, int idade) {
        this.idade = idade;
        this.nome = nome;
        this.horarioInicio = Instant.now();
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
            return Duration.between(horarioInicio, horarioFinal).toMillis();
        }
        return -1;
    }

    public String toString() {
        return "Nome: " + this.nome + " - idade: " + this.idade + " anos ";
    }

    public void fimAtendimento() {
        this.horarioFinal = Instant.now();
    }

}
