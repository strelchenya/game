package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

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
    public Page<Player> getAll(int pageNumber, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort));
        return this.playerRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public long countPlayers() {
        return playerRepository.count();
    }

    @Override
    @Transactional
    public List<Player> findByNameAndTitleAndRaceAndProfession(String name, String title, String race, String profession) {
        return this.playerRepository.findByNameAndTitleAndRaceAndProfession(name, title, race, profession);
    }


    @Override
    public List<Player> findByNameContains(String name) {
        return this.playerRepository.findByNameContains(name);
    }
}
