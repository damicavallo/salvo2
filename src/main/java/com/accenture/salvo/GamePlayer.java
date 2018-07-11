package com.accenture.salvo;

import javax.persistence.*;
import java.util.*;

import static java.util.stream.Collectors.toSet;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    Set<Ship> ships = new HashSet<>();

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    Set<Salvo> salvoes = new HashSet<>();


    public GamePlayer() { }


    public  Map<String,Object> hitsRecibidos(Salvo unSalvo) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        for (Ship ship : ships) {
            int hit = 0;
            for (int i = 0; i < unSalvo.getLocations().size(); ++i) {
                for (int j = 0; j < ship.getLocations().size(); ++j) {
                    if (unSalvo.getLocations().get(i) == ship.getLocations().get(j)) {
                        hit += 1;
                    }
                }
            }
            dto.put(ship.getTipoBarco() + "Hits", hit);
        }
        return dto;
    }




    public void setListShips(List<Ship> listShips) {
        listShips.forEach(ship -> ship.setGamePlayer(this));
        this.setShips(listShips.stream().collect(toSet()));
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public GamePlayer(Game game, Player player) {
        fechaCreacion= new Date();
        this.game = game;
        this.player = player;
    }
    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public long getId() {
        return id;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships.addAll(ships);
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(Set<Salvo> salvos) {
        this.salvoes = salvos;
    }

    public Score getScoreDeJuego(){
        return player.getScoreUnGame(game);}


}




