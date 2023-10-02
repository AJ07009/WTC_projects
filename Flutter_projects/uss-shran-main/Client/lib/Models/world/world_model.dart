import 'dart:convert';

import 'maze_model.dart';

class World {
  late String worldID;

  late Maze maze;

  late String topLeft;
  late String bottomRight;

  late int topX = 0;
  late int topY = 0;
  late int bottomX = 0;
  late int bottomY = 0;

  void setMaze(Maze newMaze) {
    // Maze maze = Maze.fromJson(jsonDecode(newMaze));
    maze = maze;
  }

  void setTopLeft(String topLeft) {
    final splitPos = topLeft.split(',');
    List splitList = [];
    for (int i = 0; i < splitPos.length; i++) {
      splitList.add(splitPos[i]);
    }
    topX = splitList[0];
    topY = splitList[1];
  }

  void setBottomRight(String bottomRight) {
    final splitPos = bottomRight.split(',');
    List splitList = [];
    for (int i = 0; i < splitPos.length; i++) {
      splitList.add(splitPos[i]);
    }
    bottomX = splitList[0];
    bottomY = splitList[1];
  }

  String getTopLeft() {
    return "" + topX.toString() + ", " + topY.toString();
  }

  String getBottomRight() {
    return "" + bottomX.toString() + ", " + bottomY.toString();
  }

  World create(String topLeft, String bottomRight, Maze maze) {
    World world =
        World(maze: Maze(obstacles: []), topLeft: "0,0", bottomRight: "0,0");

    world.setTopLeft(topLeft);
    world.setBottomRight(bottomRight);
    world.setMaze(maze);

    return world;
  }

  World({
    required this.maze,
    required this.topLeft,
    required this.bottomRight,
  });

  factory World.fromJson(Map<String, dynamic> json) {
    return World(
        maze: Maze.fromJson(jsonDecode(json["Maze"])),
        topLeft: json["TopLeft"],
        bottomRight: json["BottomRight"]);
  }
}
