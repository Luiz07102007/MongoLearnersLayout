package com.example.mongolearners;

public class AulasModel {
    String nome;


    public AulasModel() {
    }

    public AulasModel(String nome, String img_url) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
