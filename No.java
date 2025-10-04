package EstruturaDados.TrabalhoPratico;

public class No {

    private Pessoa pessoa;
    private No proximo;

    public No(Pessoa pessoa) {
        this.pessoa = pessoa;
        this.proximo = null;
    }

    public No getProximo() {
        return proximo;
    }

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }


}