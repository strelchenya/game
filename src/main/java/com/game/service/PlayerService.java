package com.game.service;

import com.game.entity.Player;

import java.util.List;

public interface PlayerService {

    Player getById(Long id);

    List<Player> getAll();

    void save(Player player);

    void delete(Long id);

}
