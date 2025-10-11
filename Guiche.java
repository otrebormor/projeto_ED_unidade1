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

     // Guiche.java - Método atender() Corrigido
    public void atender(Pessoa p, boolean foiPrioritario) {
        this.pessoaAtendida = p;
        if (p == null) return;
        
        p.fimAtendimento();

        if (this.preferencial) {
            if (foiPrioritario) {
                if (p.getIdade() >= 80) {
                    // Atendeu 80+: Incrementa 80+, zera 65+ para recomeçar o ciclo 80+
                    this.contadorPrioritarios80++;
                    this.contadorPrioritarios65 = 0; 
                    
                } else if (p.getIdade() >= 65) {
                    // CRÍTICO: Atendeu 65+: Zera 80+ para iniciar o próximo ciclo 80+
                    // E ZERA 65+ (para permitir o próximo 65+ entrar no próximo ciclo)
                    this.contadorPrioritarios80 = 0; 
                    this.contadorPrioritarios65 = 0; // <--- MUDANÇA AQUI
                }
                
            } else {
                // Atendeu Comum (via Starvation/Ociosidade)
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
