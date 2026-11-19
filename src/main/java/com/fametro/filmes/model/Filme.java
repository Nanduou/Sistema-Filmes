package com.fametro.filmes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import java.math.BigDecimal;

@Entity
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título do filme é obrigatório")
    private String titulo;

    @NotNull(message = "O ano do filme é obrigatório")
    @Positive(message = "O ano deve ser positivo")
    private Integer ano;

    @NotNull(message = "A nota é obrigatória")
    @DecimalMin(value = "0.0", message = "A nota mínima é 0")
    @DecimalMax(value = "10.0", message = "A nota máxima é 10")
    @Column(precision = 3, scale = 1)
    private BigDecimal nota;

    @ManyToOne
    @JoinColumn(name = "produtora_id", nullable = false)
    private Produtora produtora;

    @ManyToOne
    @JoinColumn(name = "diretor_id", nullable = false)
    private Diretor diretor;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Filme() {
    }

    public Filme(String titulo, Integer ano, Produtora produtora,
                 Diretor diretor, Categoria categoria) {
        this.titulo = titulo;
        this.ano = ano;
        this.produtora = produtora;
        this.diretor = diretor;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    @Transient
    public String getClassificacao() {
        if (nota == null) return "Sem avaliação";
        if (nota.compareTo(new BigDecimal("8.0")) >= 0) return "Destaque";
        if (nota.compareTo(new BigDecimal("6.0")) >= 0) return "Bom";
        if (nota.compareTo(new BigDecimal("4.0")) >= 0) return "Regular";
        return "Baixa avaliação";
    }

    public Produtora getProdutora() {
        return produtora;
    }

    public void setProdutora(Produtora produtora) {
        this.produtora = produtora;
    }

    public Diretor getDiretor() {
        return diretor;
    }

    public void setDiretor(Diretor diretor) {
        this.diretor = diretor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
