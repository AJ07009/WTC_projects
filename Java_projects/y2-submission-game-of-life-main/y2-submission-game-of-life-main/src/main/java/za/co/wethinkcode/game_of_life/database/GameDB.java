package za.co.wethinkcode.game_of_life.database;

import za.co.wethinkcode.game_of_life.domain.World;

import java.sql.SQLException;
import java.util.List;

public interface GameDB {

    World get(Integer id) throws SQLException;

    World create(World world) throws SQLException;

    List<World> all();
}
