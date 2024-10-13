package com.example.screenSound.model;

import jakarta.persistence.*;

@Entity
@Table(name = "musicas")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    private Artist artist;

    public Music(String nomeMusica, Artist artist) {
        this.titulo = nomeMusica;
        this.artist = artist;
    }

    public Music() {}

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Music{" +
                "titulo='" + titulo + '\'' +
                ", artist=" + artist.getNome() +
                '}';
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
