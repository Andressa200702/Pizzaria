package br.senac.sp.pizzaria.model;

public enum Tamanho {
    BROTO("Broto", 4),
    MEDIA("Média", 6),
    GRANDE("Grande", 8);
    private String descricao;
    private int pedacos;

    Tamanho(String descricao, int pedacos) {
        this.descricao = descricao;
        this.pedacos = pedacos;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao + ", " + pedacos + " pedaços";
    }
}
