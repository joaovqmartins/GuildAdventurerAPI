package br.com.infnet.GuildaAventureiros.repository;

import br.com.infnet.GuildaAventureiros.model.Aventureiro;
import br.com.infnet.GuildaAventureiros.model.Classe;
import br.com.infnet.GuildaAventureiros.model.Companheiro;
import br.com.infnet.GuildaAventureiros.model.Especie;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AventureiroRepository {

    private final List<Aventureiro> aventureiros = new ArrayList<>();
    private final AtomicLong geradorIdAventureiro = new AtomicLong(1);

    @PostConstruct
    public void inicializarDados() {
        Classe[] classes = Classe.values();
        for (int i = 1; i <= 100; i++) {
            Classe classeAleatoria = classes[i % classes.length];
            int nivelAleatorio = (i % 20) + 1;

            Companheiro pet = null;
            if (i % 3 == 0) {
                pet = new Companheiro("Pet " + i, Especie.LOBO, 100);
            }

            aventureiros.add(new Aventureiro(
                    geradorIdAventureiro.getAndIncrement(),
                    "Aventureiro " + i,
                    classeAleatoria,
                    nivelAleatorio,
                    true,
                    pet
            ));
        }
    }

    public List<Aventureiro> getTodos() {
        return aventureiros;
    }

    public Aventureiro salvar(Aventureiro aventureiro) {
        aventureiro.setId(geradorIdAventureiro.getAndIncrement());
        aventureiros.add(aventureiro);
        return aventureiro;
    }
}