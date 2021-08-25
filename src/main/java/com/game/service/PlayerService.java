package com.game.service;

import com.game.entity.Player;

import java.util.List;

public interface PlayerService {

    Player getById(Long id);

    List<Player> getAllPlayers();

    void savePlayer(Player player);

    void deletePlayer(Long id);

}
