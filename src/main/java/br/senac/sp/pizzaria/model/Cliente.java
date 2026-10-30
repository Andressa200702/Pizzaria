package br.senac.sp.pizzaria.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true, columnDefinition = "varchar(11)")
    private String telefone;
    @Column(columnDefinition = "varchar(500)")
    private String endereco;
    private LocalDate nascimento;
    @Column(columnDefinition = "varchar(11)")
    private String cpf;

}


