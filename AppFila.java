package EstruturaDados.TrabalhoPratico;

import java.util.Scanner;

public class AppFila {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        FilaAtendimento filaComum = new FilaAtendimento();
        FilaAtendimento fila65 = new FilaAtendimento();
        FilaAtendimento fila80 = new FilaAtendimento();
        FilaAtendimento guarda = new FilaAtendimento();


        System.out.print("Digite o número de guichês: ");
        int qtd = sc.nextInt();
        Guiche[] guiches = new Guiche[qtd];


        int comuns = qtd / 2;

        for (int i = 0; i < qtd; i++) {
            if (i < comuns) {
                guiches[i] = new Guiche(false);
            } else {
                guiches[i] = new Guiche(true);
            }
        }

        int opcao = 0;

        while (opcao != 8) {
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Incluir pessoa");
            System.out.println("2 - Liberar atendimento");
            System.out.println("3 - Exibir estado da fila");
            System.out.println("4 - Exibir guichês");
			System.out.println("5 - Tempo médio das pessoas em geral: ");
			System.out.println("6 - Tempo médio das pessoas comuns: ");
			System.out.println("7 - Tempo médio das pessoas prioritárias: ");
			System.out.println("8 - Sair");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    inserePessoa(sc, filaComum, fila65, fila80);
                    break;

                case 2:
                    GerenciadorDeAtendimento.liberaAtendimento(guiches, filaComum, fila65, fila80);
                    break;
                case 3:
                    GerenciadorDeAtendimento.simulaAtendimento(guiches, filaComum, fila65, fila80, guarda);
                    break;

                case 4:
                    exibeGuiches(guiches);
                    break;

                case 5:
                    System.out.println("O tempo médio de todos os atendidos foi: " + tempoMedio(fila65, fila80, filaComum) + " milissegundos");
                    break;
                case 6:
                    System.out.println("O tempo médio das pessoas comuns foi: " + tempoMedio(filaComum) + " milissegundos");
                    break;
                case 7:
                    System.out.println("O tempo médio das pessoas prioritárias foi: " + tempoMedio(fila65, fila80) + " milissegundos");
                    break;
                case 8:
                    System.out.println("Saindo da aplicação....");
                    break;

                default:
                    System.out.println("Digite uma opção válida! tente novamente.");
            }
        }
        sc.close();

    }

    public static void inserePessoa(Scanner sc, FilaAtendimento filaComum, FilaAtendimento fila65, FilaAtendimento fila80) {

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

    public static void exibeGuiches(Guiche[] guiches) {
        for (int i = 0; i < guiches.length; i++) {
            System.out.println("Guichê " + (i + 1) + ": " + guiches[i]);
        }
    }

    public static double tempoMedio(FilaAtendimento fila1) {
        if (fila1.getNumPessoa() != 0) {
            return fila1.getTempoTotal() / fila1.getNumPessoa();
        }
        return 0;
    }

    public static double tempoMedio(FilaAtendimento fila1, FilaAtendimento fila2) {
        if (fila1.getNumPessoa() != 0 || fila2.getNumPessoa() != 0) {
            return (fila1.getTempoTotal() + fila2.getTempoTotal()) / (fila1.getNumPessoa() + fila2.getNumPessoa());
        }
        return 0;
    }

    public static double tempoMedio(FilaAtendimento fila1, FilaAtendimento fila2, FilaAtendimento fila3) {
        if (fila1.getNumPessoa() != 0 || fila2.getNumPessoa() != 0 || fila3.getNumPessoa() != 0) {
            return (fila1.getTempoTotal() + fila2.getTempoTotal() + fila3.getTempoTotal()) / (fila1.getNumPessoa() + fila2.getNumPessoa() + fila3.getNumPessoa());
        }
        return 0;
    }


}
