package com.example.mongolearners;

public class UserModel {
        String name;
        String email;
        String senha;
        Integer aulasMongo;
        Integer aulasNode;

        public UserModel() {

        }

        public UserModel(String name, String email, String senha, Integer aulasMongo, Integer aulasNode) {
            this.name = name;
            this.email = email;
            this.senha = senha;
            this.aulasMongo = aulasMongo;
            this.aulasNode = aulasNode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

    public Integer getAulasMongo() {
        return aulasMongo;
    }

    public void setAulasMongo(Integer aulasMongo) {
        this.aulasMongo = aulasMongo;
    }

    public Integer getAulasNode() {
        return aulasNode;
    }

    public void setAulasNode(Integer aulasNode) {
        this.aulasNode = aulasNode;
    }
}
