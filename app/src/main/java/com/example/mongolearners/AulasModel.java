package com.example.mongolearners;

public class AulasModel {
    String nome;
    String conteudo;

    public AulasModel() {
    }

    public AulasModel(String nome, String conteudo) {
        this.nome = nome;
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
