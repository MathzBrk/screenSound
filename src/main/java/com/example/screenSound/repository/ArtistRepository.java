package com.example.screenSound.repository;

import com.example.screenSound.model.Artist;
import com.example.screenSound.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository <Artist, Long> {
    Optional<Artist> findByNomeContainingIgnoreCase(String nome);
    @Query("SELECT m FROM Artist a JOIN a.musicas m WHERE a.nome ILIKE %:nomeArtista")
    List<Music> buscaMusicaPorArtista(String nomeArtista);
}
