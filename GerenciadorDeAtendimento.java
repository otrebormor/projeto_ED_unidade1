package EstruturaDados.TrabalhoPratico;

public class GerenciadorDeAtendimento {

	private static int contadorGlobalCiclo = 0;

	public static void liberaAtendimento(Guiche[] guiches, FilaAtendimento filaComum, FilaAtendimento fila65,
			FilaAtendimento fila80) {

		// Configurações de Cota (acessando as constantes da classe, ou declarando-as)
		final int MAX_80 = 2;
		final int MAX_65 = 1;
		final int MAX_CICLO = MAX_80 + MAX_65;

		// A variável 'contadorGlobalCiclo' é assumida como uma variável de estado da
		// classe (static)
		// Se não for 'static', você deve passá-la como parâmetro e retorná-la.

		// =======================================================
		// 1. PRIMEIRA ITERAÇÃO: PROCESSAR APENAS GUICHÊS PREFERENCIAIS
		// (A prioridade de cota deve vir sempre primeiro)
		// =======================================================
		for (Guiche g : guiches) {
			if (g.isPreferencial()) {
				Pessoa atendida = null;
				boolean foiPrioritario = false;

				// A. STARVATION: Se o ciclo travou E houver Comum
				if (!filaComum.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_CICLO) {
					atendida = filaComum.desenfileirar();
					GerenciadorDeAtendimento.contadorGlobalCiclo = 0; // Reinicia o ciclo após Starvation
					foiPrioritario = false;

				} else {
					// B. ESCALONAMENTO POR PRIORIDADE E COTA GLOBAL

					// 80+ Tenta entrar na cota 1 ou 2 (contador 0 ou 1)
					if (!fila80.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo < MAX_80) {
						atendida = fila80.desenfileirar();
						GerenciadorDeAtendimento.contadorGlobalCiclo++;
						foiPrioritario = true;

						// 65+ Tenta entrar na cota 3 (contador 2)
					} else if (!fila65.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_80
							&& GerenciadorDeAtendimento.contadorGlobalCiclo < MAX_CICLO) {
						atendida = fila65.desenfileirar();
						GerenciadorDeAtendimento.contadorGlobalCiclo++; // Vai para 3 (será zerado na próxima checagem)
						foiPrioritario = true;

						// C. OCIOSIDADE: Atende Comum se TODAS as filas prioritárias estiverem VAZIAS
					} else if (!filaComum.isVazia() && fila80.isVazia() && fila65.isVazia()) {
						atendida = filaComum.desenfileirar();
						foiPrioritario = false;
					}
				}

				// D. RECUPERAÇÃO/CICLO FORÇADO (Se o ciclo travou e há prioritários)
				if (atendida == null) {
					if (!fila80.isVazia()) {
						atendida = fila80.desenfileirar();
						// Força o início do ciclo 80+
						GerenciadorDeAtendimento.contadorGlobalCiclo = 1;
						foiPrioritario = true;
					} else if (!fila65.isVazia()) {
						atendida = fila65.desenfileirar();
						// Força o fim do ciclo (será zerado na checagem do MAX_CICLO)
						GerenciadorDeAtendimento.contadorGlobalCiclo = MAX_CICLO;
						foiPrioritario = true;
					}
				}

				// Reinicia o ciclo se o 65+ acabou de ser atendido (contador atingiu o máximo)
				if (GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_CICLO) {
					GerenciadorDeAtendimento.contadorGlobalCiclo = 0;
				}

				if (atendida != null) {
					// O Guichê Preferencial agora não tem mais contadores internos de cota
					g.atender(atendida, foiPrioritario);
					System.out.println("Guichê Preferencial: Atendeu " + atendida.getNome() + " (" + atendida.getIdade()
							+ " anos)");
				} else {
					System.out.println("Guichê Preferencial: Livre (Filas Vazias).");
				}
			}
		}

		// =======================================================
		// 2. SEGUNDA ITERAÇÃO: PROCESSAR APENAS GUICHÊS COMUNS
		// (Lógica de ociosidade INVERTIDA para não esmagar o 65+)
		// =======================================================
		for (Guiche g : guiches) {
			if (!g.isPreferencial()) {
				Pessoa atendida = null;
				boolean foiPrioritario = false;

				if (!filaComum.isVazia()) {
					atendida = filaComum.desenfileirar();
					foiPrioritario = false;
				}
				// Ociosidade INVERTIDA: 65+ primeiro (para não roubar o 80+ que o Pref.
				// atenderia)
				else if (!fila65.isVazia()) {
					atendida = fila65.desenfileirar();
					foiPrioritario = true;
				}
				// 80+ só depois
				else if (!fila80.isVazia()) {
					atendida = fila80.desenfileirar();
					foiPrioritario = true;
				}

				if (atendida != null) {
					// Guichê Comum não usa contadores.
					g.atender(atendida, foiPrioritario);
					System.out.println(
							"Guichê Comum: Atendeu " + atendida.getNome() + " (" + atendida.getIdade() + " anos)");
				} else {
					System.out.println("Guichê Comum: Livre (Filas Vazias).");
				}
			}
		}
	}

	public static void simulaAtendimento(Guiche[] guiches, FilaAtendimento filaComum, FilaAtendimento fila65,
			FilaAtendimento fila80, FilaAtendimento guarda) {

		// Configurações de Cota (acessando as constantes da classe, ou declarando-as)
		final int MAX_80 = 2;
		final int MAX_65 = 1;
		final int MAX_CICLO = MAX_80 + MAX_65;
		
		FilaAtendimento copiaFilaComum = filaComum.copiarFila();
		FilaAtendimento copiaFila65 = fila65.copiarFila();
		FilaAtendimento copiaFila80 = fila80.copiarFila();
		guarda.limpaFila();
		while (!copiaFilaComum.isVazia() || !copiaFila65.isVazia() || !copiaFila80.isVazia()) {

			// A variável 'contadorGlobalCiclo' é assumida como uma variável de estado da
			// classe (static)
			// Se não for 'static', você deve passá-la como parâmetro e retorná-la.

			// =======================================================
			// 1. PRIMEIRA ITERAÇÃO: PROCESSAR APENAS GUICHÊS PREFERENCIAIS
			// (A prioridade de cota deve vir sempre primeiro)
			// =======================================================
			for (Guiche g : guiches) {
				if (g.isPreferencial()) {
					Pessoa atendida = null;
					boolean foiPrioritario = false;

					// A. STARVATION: Se o ciclo travou E houver Comum
					if (!copiaFilaComum.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_CICLO) {
						atendida = copiaFilaComum.desenfileirar();
						GerenciadorDeAtendimento.contadorGlobalCiclo = 0; // Reinicia o ciclo após Starvation
						foiPrioritario = false;
						guarda.enfileirar(atendida);

					} else {
						// B. ESCALONAMENTO POR PRIORIDADE E COTA GLOBAL

						// 80+ Tenta entrar na cota 1 ou 2 (contador 0 ou 1)
						if (!copiaFila80.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo < MAX_80) {
							atendida = copiaFila80.desenfileirar();
							GerenciadorDeAtendimento.contadorGlobalCiclo++;
							foiPrioritario = true;
							guarda.enfileirar(atendida);

							// 65+ Tenta entrar na cota 3 (contador 2)
						} else if (!copiaFila65.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_80
								&& GerenciadorDeAtendimento.contadorGlobalCiclo < MAX_CICLO) {
							atendida = copiaFila65.desenfileirar();
							GerenciadorDeAtendimento.contadorGlobalCiclo++; // Vai para 3 (será zerado na próxima
																			// checagem)
							foiPrioritario = true;
							guarda.enfileirar(atendida);

							// C. OCIOSIDADE: Atende Comum se TODAS as filas prioritárias estiverem VAZIAS
						} else if (!copiaFilaComum.isVazia() && copiaFila80.isVazia() && copiaFila65.isVazia()) {
							atendida = copiaFilaComum.desenfileirar();
							foiPrioritario = false;
							guarda.enfileirar(atendida);
						}
					}

					// D. RECUPERAÇÃO/CICLO FORÇADO (Se o ciclo travou e há prioritários)
					if (atendida == null) {
						if (!copiaFila80.isVazia()) {
							atendida = copiaFila80.desenfileirar();
							// Força o início do ciclo 80+
							GerenciadorDeAtendimento.contadorGlobalCiclo = 1;
							foiPrioritario = true;
							guarda.enfileirar(atendida);
						} else if (!copiaFila65.isVazia()) {
							atendida = copiaFila65.desenfileirar();
							// Força o fim do ciclo (será zerado na checagem do MAX_CICLO)
							GerenciadorDeAtendimento.contadorGlobalCiclo = MAX_CICLO;
							foiPrioritario = true;
							guarda.enfileirar(atendida);
						}
					}

					// Reinicia o ciclo se o 65+ acabou de ser atendido (contador atingiu o máximo)
					if (GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_CICLO) {
						GerenciadorDeAtendimento.contadorGlobalCiclo = 0;
					}

				}
			}

			// =======================================================
			// 2. SEGUNDA ITERAÇÃO: PROCESSAR APENAS GUICHÊS COMUNS
			// (Lógica de ociosidade INVERTIDA para não esmagar o 65+)
			// =======================================================
			for (Guiche g : guiches) {
				if (!g.isPreferencial()) {
					Pessoa atendida = null;
					boolean foiPrioritario = false;

					if (!copiaFilaComum.isVazia()) {
						atendida = copiaFilaComum.desenfileirar();
						foiPrioritario = false;
						guarda.enfileirar(atendida);
					}
					// Ociosidade INVERTIDA: 65+ primeiro (para não roubar o 80+ que o Pref.
					// atenderia)
					else if (!copiaFila65.isVazia()) {
						atendida = copiaFila65.desenfileirar();
						foiPrioritario = true;
						guarda.enfileirar(atendida);
					}
					// 80+ só depois
					else if (!copiaFila80.isVazia()) {
						atendida = copiaFila80.desenfileirar();
						foiPrioritario = true;
						guarda.enfileirar(atendida);
					}

				}
			}
			
		}
		guarda.imprimirFila();
	}

}
