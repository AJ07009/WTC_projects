import 'package:client/Models/world/world_model.dart';
import 'package:client/Services/http_requests.dart';
import 'package:flutter/cupertino.dart';

import 'maze_model.dart';

class WorldViewModel extends ChangeNotifier {
  late List<WorldModel> worlds = [];

  Future<void> fetchWorldData(String ip, String port) async {
    worlds.clear();
    final world = await getWorld(ip, port);
    worlds.add(WorldModel(world: world));
    notifyListeners();
    // worlds = world
  }

  int getNumOfBlocksPerRow() {
    int lowerX = int.parse(worlds.first.world.topLeft.split(",")[0]).abs();
    int upperX = int.parse(worlds.first.world.bottomRight.split(",")[0]).abs();
    return lowerX + upperX + 1;
  }

  int getNumOfRows() {
    int lowerY = int.parse(worlds.first.world.bottomRight.split(",")[1]).abs();
    int upperY = int.parse(worlds.first.world.topLeft.split(",")[1]).abs();
    return upperY + lowerY + 1;
  }
}

class WorldModel {
  late final World world;

  WorldModel({required this.world});

  String getTopLeft() {
    return "" + world.topX.toString() + ", " + world.topY.toString();
  }

  String getBottomRight() {
    return "" + world.bottomX.toString() + ", " + world.bottomY.toString();
  }

  List<dynamic> getWorldObs() {
    return world.maze.obstacles;
  }

  Maze getWorldMaze() {
    return world.maze;
  }
}
