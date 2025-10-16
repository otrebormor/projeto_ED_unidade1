package EstruturaDados.TrabalhoPratico;

public class GerenciadorDeAtendimento {

    private static int contadorGlobalCiclo = 0;

    public static void liberaAtendimento(Guiche[] guiches, FilaAtendimento filaComum, FilaAtendimento fila65,
                                         FilaAtendimento fila80) {


        final int MAX_80 = 2;
        final int MAX_65 = 1;
        final int MAX_CICLO = MAX_80 + MAX_65;


        for (Guiche g : guiches) {
            if (g.isPreferencial()) {
                Pessoa atendida = null;
                boolean foiPrioritario = false;


                if (!filaComum.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_CICLO) {
                    atendida = filaComum.desenfileirar();
                    GerenciadorDeAtendimento.contadorGlobalCiclo = 0;
                    foiPrioritario = false;

                } else {

                    if (!fila80.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo < MAX_80) {
                        atendida = fila80.desenfileirar();
                        GerenciadorDeAtendimento.contadorGlobalCiclo++;
                        foiPrioritario = true;


                    } else if (!fila65.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_80
                            && GerenciadorDeAtendimento.contadorGlobalCiclo < MAX_CICLO) {
                        atendida = fila65.desenfileirar();
                        GerenciadorDeAtendimento.contadorGlobalCiclo++;
                        foiPrioritario = true;


                    } else if (!filaComum.isVazia() && fila80.isVazia() && fila65.isVazia()) {
                        atendida = filaComum.desenfileirar();
                        foiPrioritario = false;
                    }
                }


                if (atendida == null) {
                    if (!fila80.isVazia()) {
                        atendida = fila80.desenfileirar();

                        GerenciadorDeAtendimento.contadorGlobalCiclo = 1;
                        foiPrioritario = true;
                    } else if (!fila65.isVazia()) {
                        atendida = fila65.desenfileirar();

                        GerenciadorDeAtendimento.contadorGlobalCiclo = MAX_CICLO;
                        foiPrioritario = true;
                    }
                }


                if (GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_CICLO) {
                    GerenciadorDeAtendimento.contadorGlobalCiclo = 0;
                }

                if (atendida != null) {

                    g.atender(atendida, foiPrioritario);
                    System.out.println("Guichê Preferencial: Atendeu " + atendida.getNome() + " (" + atendida.getIdade()
                            + " anos)");
                } else {
                    System.out.println("Guichê Preferencial: Livre (Filas Vazias).");
                }
            }
        }


        for (Guiche g : guiches) {
            if (!g.isPreferencial()) {
                Pessoa atendida = null;
                boolean foiPrioritario = false;

                if (!filaComum.isVazia()) {
                    atendida = filaComum.desenfileirar();
                    foiPrioritario = false;
                } else if (!fila65.isVazia()) {
                    atendida = fila65.desenfileirar();
                    foiPrioritario = true;
                } else if (!fila80.isVazia()) {
                    atendida = fila80.desenfileirar();
                    foiPrioritario = true;
                }

                if (atendida != null) {

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


        final int MAX_80 = 2;
        final int MAX_65 = 1;
        final int MAX_CICLO = MAX_80 + MAX_65;

        FilaAtendimento copiaFilaComum = filaComum.copiarFila();
        FilaAtendimento copiaFila65 = fila65.copiarFila();
        FilaAtendimento copiaFila80 = fila80.copiarFila();
        guarda.limpaFila();
        while (!copiaFilaComum.isVazia() || !copiaFila65.isVazia() || !copiaFila80.isVazia()) {


            for (Guiche g : guiches) {
                if (g.isPreferencial()) {
                    Pessoa atendida = null;
                    
                    if (!copiaFilaComum.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_CICLO) {
                        atendida = copiaFilaComum.desenfileirar();
                        GerenciadorDeAtendimento.contadorGlobalCiclo = 0;                       
                        guarda.enfileirar(atendida);

                    } else {

                        if (!copiaFila80.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo < MAX_80) {
                            atendida = copiaFila80.desenfileirar();
                            GerenciadorDeAtendimento.contadorGlobalCiclo++;                            
                            guarda.enfileirar(atendida);


                        } else if (!copiaFila65.isVazia() && GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_80
                                && GerenciadorDeAtendimento.contadorGlobalCiclo < MAX_CICLO) {
                            atendida = copiaFila65.desenfileirar();
                            GerenciadorDeAtendimento.contadorGlobalCiclo++;                           
                            guarda.enfileirar(atendida);


                        } else if (!copiaFilaComum.isVazia() && copiaFila80.isVazia() && copiaFila65.isVazia()) {
                            atendida = copiaFilaComum.desenfileirar();                           
                            guarda.enfileirar(atendida);
                        }
                    }


                    if (atendida == null) {
                        if (!copiaFila80.isVazia()) {
                            atendida = copiaFila80.desenfileirar();
                            GerenciadorDeAtendimento.contadorGlobalCiclo = 1;                            
                            guarda.enfileirar(atendida);
                        } else if (!copiaFila65.isVazia()) {
                            atendida = copiaFila65.desenfileirar();
                            GerenciadorDeAtendimento.contadorGlobalCiclo = MAX_CICLO;                           
                            guarda.enfileirar(atendida);
                        }
                    }


                    if (GerenciadorDeAtendimento.contadorGlobalCiclo >= MAX_CICLO) {
                        GerenciadorDeAtendimento.contadorGlobalCiclo = 0;
                    }

                }
            }

            for (Guiche g : guiches) {
                if (!g.isPreferencial()) {
                    Pessoa atendida = null;                   

                    if (!copiaFilaComum.isVazia()) {
                        atendida = copiaFilaComum.desenfileirar();                        
                        guarda.enfileirar(atendida);
                    } else if (!copiaFila65.isVazia()) {
                        atendida = copiaFila65.desenfileirar();                        
                        guarda.enfileirar(atendida);
                    } else if (!copiaFila80.isVazia()) {
                        atendida = copiaFila80.desenfileirar();                       
                        guarda.enfileirar(atendida);
                    }

                }
            }

        }
        guarda.imprimirFila();
    }

}

