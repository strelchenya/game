package com.game.service;

import com.game.entity.Player;
import com.game.entity.PlayersFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Optional<Player> getById(Long id);

    Player create(Player player);

    void delete(Long id);

    List<Player> getAll(PlayersFilter playersFilter, Integer pageNumber, Integer pageSize, String order);

    Integer getCount(PlayersFilter playersFilter, Integer pageNumber, Integer pageSize, String order);

    boolean playerIsValid(Player player);

    Player edit(Player player, Long id);

    boolean idIsNotValid(String stringId);
    boolean upgradeIsNotValid(Player player, Long id);
}
