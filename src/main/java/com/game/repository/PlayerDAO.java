package com.game.repository;

import com.game.entity.Player;

import java.util.List;

public interface PlayerDAO {

     List<Player> getAllEmployees();

     void saveEmployee(Player employee);

     Player getEmployee(Long id);

    void deleteEmployee(Long id);
}
