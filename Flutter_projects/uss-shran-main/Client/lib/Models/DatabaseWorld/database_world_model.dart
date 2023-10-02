import 'dart:convert';

import 'package:client/Models/world/maze_model.dart';

class DatabaseWorld {
  late String worldID;

  late String bottomRight;
  late String topLeft;

  late Maze maze;

  late String maxShots;
  late String maxShields;
  late String reloadTime;
  late String repairTime;
  late String visibility;
  late String mineSetTime;

  DatabaseWorld create(
      String newID,
      String newBottomRight,
      String newTopLeft,
      Maze newMaze,
      String newMaxShots,
      String newMaxShield,
      String newReloadTime,
      String newRepairTime,
      String newVisibility,
      String newMineSetTime) {
    DatabaseWorld databaseWorld = DatabaseWorld(
        worldID: newID,
        bottomRight: newBottomRight,
        topLeft: newTopLeft,
        maze: newMaze,
        maxShots: newMaxShots,
        visibility: newVisibility,
        mineSetTime: newMineSetTime,
        reloadTime: newReloadTime,
        repairTime: newRepairTime,
        maxShields: newMaxShield);
    return databaseWorld;
  }

  DatabaseWorld(
      {required this.worldID,
      required this.bottomRight,
      required this.topLeft,
      required this.maze,
      required this.maxShots,
      required this.visibility,
      required this.mineSetTime,
      required this.reloadTime,
      required this.repairTime,
      required this.maxShields});

  factory DatabaseWorld.fromJson(Map<String, dynamic> json) {
    return DatabaseWorld(
        worldID: json["WorldID"],
        bottomRight: json["BottomRight"],
        topLeft: json["TopLeft"],
        maze: Maze.fromJson(jsonDecode(json["Maze"])),
        maxShots: json["MaxShots"],
        visibility: json["Visibility"],
        mineSetTime: json["MineSetTime"],
        reloadTime: json["ReloadTime"],
        repairTime: json["RepairTime"],
        maxShields: json["MaxShields"]);
  }
}
