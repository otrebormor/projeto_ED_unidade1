package EstruturaDados.TrabalhoPratico;

import java.util.Scanner;

public class AppFila {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Pessoa p1, p2, p3, p4, p5, p6, p7,p8,p9,p10,p11,p12;
		p1 = new Pessoa("jose1", 65);
		p2 = new Pessoa("maria1", 80);
		p3 = new Pessoa("josefa1", 80);
		p4 = new Pessoa("jose2", 65);
		p5 = new Pessoa("maria2", 80);
		p6 = new Pessoa("josefa2", 80);
		p7 = new Pessoa("jose3", 23);
		p8 = new Pessoa("maria3", 45);
		p9 = new Pessoa("josefa3", 55);
		p10 = new Pessoa("jose4", 67);
		p11 = new Pessoa("maria4", 87);
		p12 = new Pessoa("josefa4", 90);

		FilaAtendimento filaComum = new FilaAtendimento();
		FilaAtendimento fila65 = new FilaAtendimento();
		FilaAtendimento fila80 = new FilaAtendimento();

		fila65.enfileirar(p1);
		fila80.enfileirar(p2);
		fila80.enfileirar(p3);
		fila65.enfileirar(p4);
		fila80.enfileirar(p5);
		fila80.enfileirar(p6);
		filaComum.enfileirar(p7);
		filaComum.enfileirar(p8);
		filaComum.enfileirar(p9);
		fila65.enfileirar(p10);
		fila80.enfileirar(p11);
		fila80.enfileirar(p12);

		// prgunta quantos guichês
		System.out.print("Digite o número de guichês: ");
		int qtd = sc.nextInt();
		Guiche[] guiches = new Guiche[qtd];

		// dividir guichês (se ímpar, extra vai para preferenciais)
		int comuns = qtd / 2;
		int preferenciais = qtd - comuns;

		for (int i = 0; i < qtd; i++) {
			if (i < comuns) {
				guiches[i] = new Guiche(false); // comuns
			} else {
				guiches[i] = new Guiche(true); // preferenciais
			}
		}

		int opcao = 0;
		while (opcao != 5) {
			System.out.println("\n--- MENU ---");
			System.out.println("1 - Incluir pessoa");
			System.out.println("2 - Liberar atendimento");
			System.out.println("3 - Exibir filas");
			System.out.println("4 - Exibir guichês");
			System.out.println("5 - Sair");
			opcao = sc.nextInt();
			sc.nextLine();

			switch (opcao) {
			case 1:
				System.out.print("Nome: ");
				String nome = sc.nextLine();
				System.out.print("Idade: ");
				int idade = sc.nextInt();

				Pessoa p = new Pessoa(nome, idade);
				if (idade < 65)
					filaComum.enfileirar(p);
				else if (idade < 80)
					fila65.enfileirar(p);
				else
					fila80.enfileirar(p);
				break;

			case 2: //As principais alterações foram aqui
				for (Guiche g : guiches) {
					Pessoa atendida = null;
					boolean foiPrioritario = false;

					if (g.isPreferencial()) {
						int cota80 = 2; //para facilitar a troca na regra de negócio
						int cota65 = 1; //para facilitar a troca na regra de negócio

						if (!filaComum.isVazia() && g.getContadorPrioritarios80() >= cota80
								&& g.getContadorPrioritarios65() >= cota65) {
							atendida = filaComum.desenfileirar();
							foiPrioritario = false;

						} else {

							if (!fila80.isVazia() && g.getContadorPrioritarios80() < cota80) {
								atendida = fila80.desenfileirar();
								foiPrioritario = true;

							} else if (!fila65.isVazia() && g.getContadorPrioritarios65() < cota65) {
								atendida = fila65.desenfileirar();
								foiPrioritario = true;

							} else if (!filaComum.isVazia()) {
								atendida = filaComum.desenfileirar();
								foiPrioritario = false;
							}
						}

						if (atendida == null) { // para evitar que os guiches fiquem sem trabalhar
							if (!fila80.isVazia()) {
								atendida = fila80.desenfileirar();
								foiPrioritario = true;
								g.resetContador80();
								g.resetContador65();
							} else if (!fila65.isVazia()) {
								atendida = fila65.desenfileirar();
								foiPrioritario = true;
								g.resetContador80();
								g.resetContador65();
							}
						}

					} else {// como teria guiches definidos para atender so usuários comuns, ele poderia
							// ficar sem função

						if (!filaComum.isVazia()) {
							atendida = filaComum.desenfileirar();
							foiPrioritario = false;

						} else if (!fila80.isVazia()) {
							atendida = fila80.desenfileirar();
							foiPrioritario = true;
						} else if (!fila65.isVazia()) {
							atendida = fila65.desenfileirar();
							foiPrioritario = true;
						}
					}

					if (atendida != null) {//A parte do atendimento em ficou aqui.
						
						g.atender(atendida, foiPrioritario);
						System.out.println("Guichê " + (g.isPreferencial() ? "P" : "C") + ": Atendeu "//aqui foi para entender o que estava acontecendo
								+ atendida.getNome() + " (" + atendida.getIdade() + " anos)");
					} else {//caso as filas estejam vazias
						System.out.println("Guichê " + (g.isPreferencial() ? "P" : "C") + ": Livre (Filas Vazias).");
					}
				}
				break;
			case 3:
				System.out.println("Fila comum:");
				filaComum.imprimirFila();
				System.out.println("Fila 65+:");
				fila65.imprimirFila();
				System.out.println("Fila 80+:");
				fila80.imprimirFila();
				break;

			case 4:
				for (int i = 0; i < guiches.length; i++) {
					System.out.println("Guichê " + (i + 1) + ": " + guiches[i]);
				}
				break;
			}
		}
		sc.close();

	}
}