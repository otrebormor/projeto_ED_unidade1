package EstruturaDados.TrabalhoPratico;

import java.time.Instant;

public class FilaAtendimento {
    private No inicio;
    private No fim;
    private int tempoTotal;
    private int numPessoa;

    public FilaAtendimento() {
        this.inicio = null;
        this.fim = null;
    }

    public boolean isVazia() {
        return inicio == null;
    }

    public void enfileirar(Pessoa p) {
        No novo = new No(p);
        if (isVazia()) {
            inicio = fim = novo;
        } else {
            fim.setProximo(novo);
            fim = novo;
        }
    }

    public Pessoa desenfileirar() {
        if (isVazia()) return null;
        inicio.getPessoa().fimAtendimento();
        this.tempoTotal += this.inicio.getPessoa().getDuracaoAtendimentoSegundos();
        Pessoa p = inicio.getPessoa();
        inicio = inicio.getProximo();
        if (inicio == null) fim = null;
        return p;
    }

    public void imprimirFila() {
        if (isVazia()) {
            System.out.println("Fila vazia.");
            return;
        }
        No atual = inicio;
        while (atual != null) {
            System.out.println(atual.getPessoa());
            atual = atual.getProximo();
        }
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public int getNumPessoa() {
        return numPessoa;
    }

    public No getInicio() {
        return inicio;
    }

    public FilaAtendimento copiarFila() {

        FilaAtendimento copiaDaFila = new FilaAtendimento();

        No atual = this.inicio;

        while (atual != null) {
            Pessoa pessoaOriginal = atual.getPessoa();
            Pessoa novaPessoa = new Pessoa(pessoaOriginal.getNome(), pessoaOriginal.getIdade());
            copiaDaFila.enfileirar(novaPessoa);
            atual = atual.getProximo();
        }

        return copiaDaFila;
    }

    public void limpaFila() {//Usado para limpar a fila teórica, evitando que as pessoas sejam colacadas na fila várias vezes
        this.inicio = this.fim = null;
    }

}
