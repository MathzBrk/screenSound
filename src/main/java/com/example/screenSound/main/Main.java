package com.example.screenSound.main;

import com.example.screenSound.model.Artist;
import com.example.screenSound.model.Music;
import com.example.screenSound.model.TipoArtista;
import com.example.screenSound.repository.ArtistRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private final ArtistRepository repository;
    private Scanner scanner = new Scanner(System.in);

    public Main(ArtistRepository repository) {
        this.repository = repository;
    }

    public void exibirMenu(){
        int opcao = -1;

        while(opcao != 0){
            var menu = """
                    --- Screen Sound Menu ---
                     1- Cadastrar artistas
                     2- Cadastrar músicas
                     3- Listar músicas
                     4- Buscar músicas por artistas
                     5- Remover artista
                     6- Listar artistas
                     
                     0- Sair
                    
                    """;
            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    removerArtista();
                    break;
                case 6:
                    listarTodosArtistas();
                    break;
                case 0:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarTodosArtistas() {
        List<Artist> artistas = repository.findAll();
        if(artistas.isEmpty()){
            System.out.println("Nenhum artista cadastrado!");
        } else{
            System.out.println("--- Lista de artistas ---");
            artistas.forEach(a -> System.out.println("Artista(s): " + a.getNome()));
        }
    }

    private void removerArtista() {
        System.out.println("Digite o nome do artista a ser removido: ");
        var nome = scanner.nextLine();
        Optional<Artist> artist = repository.findByNomeContainingIgnoreCase(nome);

        if(artist.isPresent()){
            repository.delete(artist.get());
            System.out.println("Artista removido com sucesso!");
        }else {
            System.out.println("Artista não encontrado!");
        }
    }

    private void buscarMusicasPorArtista() {
        System.out.println("Buscar musica de qual artista? ");
        var nome = scanner.nextLine();
        List<Music> musics = repository.buscaMusicaPorArtista(nome);
        musics.forEach(System.out::println);
    }

    private void listarMusicas() {
        List<Artist> artistas = repository.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void cadastrarMusicas() {
        System.out.println("Cadastrar musica de qual artista? ");
        var nome = scanner.nextLine();
        Optional<Artist> artist = repository.findByNomeContainingIgnoreCase(nome);
        if(artist.isPresent()){
            System.out.println("Informe o titulo da música: ");
            var nomeMusica = scanner.nextLine();
            Music music = new Music(nomeMusica, artist.get());
            artist.get().getMusicas().add(music);
            repository.save(artist.get());
        }else{
            System.out.println("Artista não encontrado");
        }
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("S")) {
            System.out.println("Digite o nome do artista: ");
            var nome = scanner.nextLine();
            System.out.println("Digite o tipo do artista: (solo, dupla ou banda)");
            var tipo = scanner.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artist artist = new Artist(nome, tipoArtista);
            repository.save(artist);
            System.out.println("Cadastrar novo artista? (S/N)");
            cadastrarNovo = scanner.nextLine().toUpperCase();
        }

    }
}
