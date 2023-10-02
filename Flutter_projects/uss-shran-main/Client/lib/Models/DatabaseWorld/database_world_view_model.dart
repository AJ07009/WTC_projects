import 'package:client/Models/world/maze_model.dart';
import 'package:client/Services/http_requests.dart';
import 'package:flutter/cupertino.dart';

import 'database_world_model.dart';

class DatabaseWorldViewModel extends ChangeNotifier {
  late List<DatabaseWorldModel> worlds = [];

  Future<void> saveWorld(String ip, String port, String worldID) async {
    await savingWorld(ip, port, worldID);
  }

  Future<void> loadWorld(String ip, String port, String worldID) async {
    await loadingWorld(ip, port, worldID);
  }

  Future<void> getAllWorlds(String ip, String port) async {
    worlds.clear();
    List<DatabaseWorld> dbWorlds = await getWorlds(ip, port);
    if (worlds.isEmpty) {
      for (var element in dbWorlds) {
        worlds.add(DatabaseWorldModel(databaseWorld: element));
      }
    }
    notifyListeners();
  }
}

class DatabaseWorldModel {
  late final DatabaseWorld databaseWorld;

  DatabaseWorldModel({required this.databaseWorld});

  String getWorldID() {
    return databaseWorld.worldID;
  }

  String getBottomPos() {
    return databaseWorld.bottomRight;
  }

  String getTopPos() {
    return databaseWorld.topLeft;
  }

  Maze getWorldMaze() {
    return databaseWorld.maze;
  }

  List<dynamic> getObstacles() {
    return databaseWorld.maze.obstacles;
  }

  List<String> getRobotStats() {
    return [
      databaseWorld.maxShields,
      databaseWorld.maxShots,
      databaseWorld.repairTime,
      databaseWorld.reloadTime,
      databaseWorld.visibility,
      databaseWorld.mineSetTime
    ];
  }
}
