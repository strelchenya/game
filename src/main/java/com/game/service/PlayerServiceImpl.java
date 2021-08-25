package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player getById(Long id) {
        return playerRepository.getOne(id);
    }

    @Override
    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    @Override
    public void save(Player player) {
        playerRepository.save(player);
    }

    @Override
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
}
