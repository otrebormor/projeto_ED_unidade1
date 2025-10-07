package EstruturaDados.TrabalhoPratico;

import java.time.Instant;

public class FilaAtendimento {
	private No inicio;
    private No fim;

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

        p.setHorarioInicio(Instant.now());
    }

    public Pessoa desenfileirar() {
        if (isVazia()) return null;
        Pessoa p = inicio.getPessoa();
        inicio = inicio.getProximo();
        if (inicio == null) fim = null;

        p.setHorarioFinal(Instant.now());
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

}
