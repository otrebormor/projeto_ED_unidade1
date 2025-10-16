package EstruturaDados.TrabalhoPratico;

public class Guiche {

    private boolean preferencial;
    private Pessoa pessoaAtendida;
    private int contadorPrioritarios80;
    private int contadorPrioritarios65;

    public Guiche(boolean preferencial) {
        this.preferencial = preferencial;
        this.pessoaAtendida = null;
        this.contadorPrioritarios65 = 0;
        this.contadorPrioritarios80 = 0;
    }

    public boolean isPreferencial() {
        return preferencial;
    }


    public void atender(Pessoa p, boolean foiPrioritario) {
        this.pessoaAtendida = p;
        if (p == null) return;

        p.fimAtendimento();

        if (this.preferencial) {
            if (foiPrioritario) {
                if (p.getIdade() >= 80) {

                    this.contadorPrioritarios80++;
                    this.contadorPrioritarios65 = 0;

                } else if (p.getIdade() >= 65) {

                    this.contadorPrioritarios80 = 0;
                    this.contadorPrioritarios65 = 0;
                }

            } else {

                this.contadorPrioritarios80 = 0;
                this.contadorPrioritarios65 = 0;
            }
        }
    }

    public int getContadorPrioritarios80() {
        return contadorPrioritarios80;
    }

    public int getContadorPrioritarios65() {
        return contadorPrioritarios65;
    }

    public void resetContador80() {
        contadorPrioritarios80 = 0;
    }

    public void resetContador65() {
        contadorPrioritarios65 = 0;
    }

    public String toString() {
        if (pessoaAtendida == null)
            return "Guichê livre";
        return "Atendendo: " + pessoaAtendida.getNome();
    }
}
