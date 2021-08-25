package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerDAOImpl implements PlayerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Player> getAllEmployees() {
        Session session = sessionFactory.getCurrentSession();
        Query<Player> query = session.createQuery("from Player", Player.class);
        List<Player> allPlayers = query.getResultList();
        return allPlayers;
    }

    @Override
    public void saveEmployee(Player employee) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(employee);
    }

    @Override
    public Player getEmployee(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Player player = session.get(Player.class, id);
        return player;
    }

    @Override
    public void deleteEmployee(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Player> query = session.createQuery("DELETE FROM Player " +
                                                            "WHERE id =:playerId");
        query.setParameter("playerId", id);
        query.executeUpdate();
    }
}
