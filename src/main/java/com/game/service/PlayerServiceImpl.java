package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    PlayerRepository playerRepository;

    @Override
    @Transactional
    public Player getById(Long id) {
        return playerRepository.findById(id).get(); //...findOne(id);
    }

    @Override
    @Transactional
    public void save(Player player) {
        playerRepository.save(player);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        playerRepository.deleteById(id); //... delete(id);
    }

    @Override
    @Transactional
    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    @Override
    @Transactional
    public Integer countPlayers() {
        return playerRepository.findAll().size();
    }
}
