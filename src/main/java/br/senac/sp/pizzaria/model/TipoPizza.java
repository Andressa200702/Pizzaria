package br.senac.sp.pizzaria.model;

public enum TipoPizza {DOCE("Doce"), SALGADA("Salgada");
    private String descricao;

    TipoPizza(String descricao){
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
