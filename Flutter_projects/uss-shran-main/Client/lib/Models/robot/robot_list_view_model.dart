import 'package:client/Models/robot/robot_model.dart';
import 'package:client/Services/http_requests.dart';
import 'package:flutter/material.dart';

class RobotListViewModel extends ChangeNotifier {
  late List<RobotViewModel> robots = [];
  late Robot robot;

  Future<void> fetchAllRobots(String ipAddress, String port) async {
    final newRobots = await getRobots(ipAddress, port);
    robots = newRobots.map((robot) => RobotViewModel(robot: robot)).toList();
    notifyListeners();
  }


  Future<void> purgeRobot(
      BuildContext context, Robot robot, String ip, String port) async {
    deleteRobot(robot, ip, port, context);
    notifyListeners();
  }

  //
  void addToList(Robot robot) {
    robots.add(RobotViewModel(robot: robot));
    notifyListeners();
  }

  //
  void removeFromList(Robot robot) {
    robots.removeWhere((element) =>
        element.robot.name.toUpperCase() == robot.name.toUpperCase());
    notifyListeners();
  }

  void setRobot(Robot robot) {
    this.robot = robot;
  }

  void forward(String ip, String port) async {
    String? position = await moveForwardRobot(ip, port, robot.name);
    if (position != null) {
      robot.setPosition(position);
    }
    notifyListeners();
  }

  void back(String ip, String port) async {
    String? position = await moveBack(ip, port, robot.name);
    if (position != null) {
      robot.setPosition(position);
    }
    notifyListeners();
  }

  void left(String ip, String port) async {
    String? direction = await turnLeft(ip, port, robot.name);
    if (direction != null) {
      robot.setDirection(direction);
    }
    notifyListeners();
  }

  void right(String ip, String port) async {
    String? direction = await turnRight(ip, port, robot.name);
    if (direction != null) {
      robot.setDirection(direction);
    }
    notifyListeners();
  }

  void look(String ip, String port) {}
}

class RobotViewModel {
  late final Robot robot;

  RobotViewModel({required this.robot});

  String get robotName {
    return robot.name;
  }

  String get robotStatus {
    return robot.status;
  }

  String get robotPosition {
    return robot.position;
  }

  String get robotX {
    final splitPos = robot.position.split(',');
    List splitList = [];
    for (int i = 0; i < splitPos.length; i++) {
      splitList.add(splitPos[i]);
    }
    return splitList[0];
  }

  String get robotY {
    final splitPos = robot.position.split(',');
    List splitList = [];
    for (int i = 0; i < splitPos.length; i++) {
      splitList.add(splitPos[i]);
    }
    return splitList[1];
  }
}
