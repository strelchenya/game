package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlayerService {

    Player getById(Long id);

    void save(Player player);

    void delete(Long id);

    Page<Player> getAll(int pageNumber, int pageSize, String sort);

    long countPlayers();

    List<Player> findByNameAndTitleAndRaceAndProfession(String name, String title, String race, String profession);
    List<Player> findByNameContains(String name);
}
