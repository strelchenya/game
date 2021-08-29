package com.game.service;

import com.game.entity.Player;
import com.game.entity.PlayersFilter;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    @Transactional
    public Optional<Player> getById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    @Transactional
    public Player create(Player player) {
        player.setLevel(getCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(getExpForNextLevel(player.getLevel(), player.getExperience()));
        return playerRepository.save(player);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Player> getAll(PlayersFilter playersFilter, Integer pageNumber, Integer pageSize, String order) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(order.toLowerCase(Locale.ROOT)));
        return playerRepository.findAll(playersFilter, paging).getContent();
    }

    @Override
    @Transactional
    public Integer getCount(PlayersFilter playersFilter, Integer pageNumber, Integer pageSize, String order) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(order.toLowerCase(Locale.ROOT)));
        return (int) playerRepository.findAll(playersFilter, paging).getTotalElements();
    }

    private Integer getCurrentLevel(Integer currentExp) {
        return (int) (Math.sqrt(2500 + 200 * currentExp) - 50) / 100;
    }

    private Integer getExpForNextLevel(Integer currentLvl, Integer currentExp) {
        return 50 * (currentLvl + 1) * (currentLvl + 2) - currentExp;
    }

    @Override
    public boolean playerIsValid(Player player) {

        return player.getName() != null &&
                player.getTitle() != null &&
                player.getRace() != null &&
                player.getProfession() != null &&
                player.getBirthday() != null &&
                player.getExperience() != null &&
                player.getName().length() <= 12 &&
                player.getTitle().length() <= 30 &&
                !player.getName().isEmpty() &&
                player.getExperience() >= 0 &&
                player.getExperience() <= 10_000_000 &&
                player.getBirthday().after(new Date(946684800000L)) &&          //2000.01.01...3000
                player.getBirthday().before(new Date(32535215999999L));

    }

    @Override
    @Transactional
    public Player edit(Player player, Long id) {
        player.setId(id);
        player.setLevel(getCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(getExpForNextLevel(player.getLevel(), player.getExperience()));

        return playerRepository.save(player);
    }

    @Override
    public boolean idIsNotValid(String stringId) {
        Long id;

        try {
            id = Long.parseLong(stringId);
        } catch (Exception e) {
            return true;
        }

        return id != null && id <= 0;
    }

    @Override
    public boolean upgradeIsNotValid(Player player, Long id) {

        Player repositoryPlayer = playerRepository.findById(id).get();

        return /*player != null &&*/
                repositoryPlayer.getName().equals(player.getName()) &&
                repositoryPlayer.getTitle().equals(player.getTitle()) &&
                repositoryPlayer.getRace() == player.getRace() &&
                repositoryPlayer.getProfession() == player.getProfession() &&
                repositoryPlayer.getExperience().equals(player.getExperience()) &&
                (repositoryPlayer.getBirthday().compareTo(player.getBirthday()) == 0) &&
                repositoryPlayer.getBanned().equals(player.getBanned());
    }
}
