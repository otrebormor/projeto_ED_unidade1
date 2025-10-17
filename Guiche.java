package EstruturaDados.TrabalhoPratico;

public class Guiche {

    private boolean preferencial;
    private Pessoa pessoaAtendida;

    public Guiche(boolean preferencial) {
        this.preferencial = preferencial;
        this.pessoaAtendida = null;

    }

    public boolean isPreferencial() {
        return preferencial;
    }

    public void atender(Pessoa p) {
        this.pessoaAtendida = p;
        if (p == null) return;
        p.fimAtendimento();

    }

    public String toString() {
        if (pessoaAtendida == null)
            return "Guichê livre";
        return "Atendendo: " + pessoaAtendida.getNome();
    }
}

