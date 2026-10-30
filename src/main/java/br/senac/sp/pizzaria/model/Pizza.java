package br.senac.sp.pizzaria.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_pizza", indexes = {
        @Index(name = "idx_sabor_tamanho",
                columnList = "sabor, tamanho", unique = true)
})
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sabor;
    private String ingredientes;
    private TipoPizza tipo;
    private Tamanho tamanho;
    private double preco;

    public String toString(){
        return String.format("%s - %s (%5.2f)", getTamanho().getDescricao(), getSabor(), getPreco());
    }
}
