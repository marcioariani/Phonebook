package com.example.marcio.agendadef.model;

import java.io.Serializable;

/**
 * Created by marcio on 19/04/2016.
 */
public class Contato implements Serializable{
    private String id;
    private String nome;
    private String telefone;
    private String photo;

    public Contato() {
    }

    public Contato(String nome, String telefone, String photo) {
        this.nome = nome;
        this.telefone = telefone;
        this.photo = photo;
    }

    public Contato(String id, String nome, String telefone, String photo) {
        this.nome = nome;
        this.telefone = telefone;
        this.photo = photo;
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getImagem() {
        return photo;
    }
    public void setImagem(String photo) {
        this.photo = photo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return telefone;
    }
    public void setValor(String telefone) {
        this.telefone = telefone;
    }
}
