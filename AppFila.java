package EstruturaDados.TrabalhoPratico;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppFila {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Pessoa> pessoasAtendidas = new ArrayList<>();

        Pessoa p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12;
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
            System.out.println("5 - Exibe atendidos");
            System.out.println("6 - Sair");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    inserePessoa(filaComum, fila65, fila80);
                    break;

                case 2:
                    liberaAtendimento(pessoasAtendidas, guiches, filaComum, fila65, fila80);
                    break;
                case 3:
                    exibeFilas(filaComum, fila65, fila80);
                    break;

                case 4:
                    exibeGuiches(guiches);
                    break;

                case 5:
                    exibeAtendidos(pessoasAtendidas);
                    break;

                case 6:
                    System.out.println("Saindo da aplicação....");
                    break;

                default:
                    System.out.println("Digite uma opção válida! tente novamente.");
            }
        }
        sc.close();

    }


    public static void inserePessoa(FilaAtendimento filaComum, FilaAtendimento fila65, FilaAtendimento fila80) {
        Scanner sc = new Scanner(System.in);

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
    }

    public static void liberaAtendimento(List<Pessoa> pessoasAtendidas, Guiche[] guiches, FilaAtendimento filaComum, FilaAtendimento fila65, FilaAtendimento fila80) {
        for (Guiche g : guiches) { //As principais alterações foram aqui
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
                System.out.println("Guichê " + (g.isPreferencial() ? "Preferencial" : "Comum") + ": Atendeu "//aqui foi para entender o que estava acontecendo
                        + atendida.getNome() + " (" + atendida.getIdade() + " anos)");
            } else {//caso as filas estejam vazias
                System.out.println("Guichê " + (g.isPreferencial() ? "Preferencial" : "Comum") + ": Livre (Filas Vazias).");
            }

            if (atendida != null) {
                g.atender(atendida, foiPrioritario);
                pessoasAtendidas.add(atendida); // Adiciona à lista de atendidos
                System.out.println("Guichê " + (g.isPreferencial() ? "Preferencial" : "Comum") + ": Atendeu "
                        + atendida.getNome() + " (" + atendida.getIdade() + " anos)");
            }

        }
    }

    public static void exibeFilas(FilaAtendimento filaComum, FilaAtendimento fila65, FilaAtendimento fila80) {
        System.out.println("Fila comum:");
        filaComum.imprimirFila();
        System.out.println("Fila 65+:");
        fila65.imprimirFila();
        System.out.println("Fila 80+:");
        fila80.imprimirFila();
    }

    public static void exibeGuiches(Guiche[] guiches) {
        for (int i = 0; i < guiches.length; i++) {
            System.out.println("Guichê " + (i + 1) + ": " + guiches[i]);
        }
    }

    public static void exibeAtendidos(List<Pessoa> pessoasAtendidas) {
        if (pessoasAtendidas.isEmpty()) {
            System.out.println("Nenhum pessoa atendida.");
        } else {
            for (Pessoa p : pessoasAtendidas) {
                System.out.println(p.getNome() + " - Duração: " + p.getDuracaoAtendimentoSegundos() + " segundos");
            }
        }
    }


}