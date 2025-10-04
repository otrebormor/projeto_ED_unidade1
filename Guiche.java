package EstruturaDados.TrabalhoPratico;

public class Guiche {

	private boolean preferencial; // true = atende prioritários, false = comuns
	private Pessoa pessoaAtendida;
	private int contadorPrioritarios80; // controla a regra 3x1
	private int contadorPrioritarios65; // controla a regra 3x1

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
			p.fimAtendimento();
			if (foiPrioritario && p.getIdade() >= 80) {
				contadorPrioritarios80++;
			} else if (foiPrioritario && p.getIdade() >= 65) {
				contadorPrioritarios65++;//alterei aqui 
				contadorPrioritarios80 = 0; //alterei aqui 
			} else {
				contadorPrioritarios80 = 0; // reset se atendeu comum
				contadorPrioritarios65 = 0; //alterei aqui 
			}
		}
	}

	public int getContadorPrioritarios80() {//alterei aqui 
		return contadorPrioritarios80;
	}
	public int getContadorPrioritarios65() {//alterei aqui 
		return contadorPrioritarios65;
	}

	public void resetContador80() {//alterei aqui 
		contadorPrioritarios80 = 0;

	}
	public void resetContador65() {//alterei aqui 
		contadorPrioritarios65 = 0;
	}

	public String toString() {
		if (pessoaAtendida == null)
			return "Guichê livre";
		return "Atendendo: " + pessoaAtendida.getNome();
	}
}
