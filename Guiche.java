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
        if (p != null) {
            if (foiPrioritario && p.getIdade() >= 80) {
                contadorPrioritarios80++;
                contadorPrioritarios65 = 0; // reset do contador 65 quando atende 80+
            } else if (foiPrioritario && p.getIdade() >= 65) {
                contadorPrioritarios65++;
                contadorPrioritarios80 = 0; // reset do contador 80 quando atende 65+
            } else {
                // Atendeu comum - reseta ambos contadores
                contadorPrioritarios80 = 0;
                contadorPrioritarios65 = 0;
            }
            p.fimAtendimento();
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

    public int getTotalPrioritarios() {
        return contadorPrioritarios65 + contadorPrioritarios80;
    }

    public String toString() {
        if (pessoaAtendida == null)
            return "Guichê livre";
        return "Atendendo: " + pessoaAtendida.getNome();
    }
}
