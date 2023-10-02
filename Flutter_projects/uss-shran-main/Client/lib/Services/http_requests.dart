import 'dart:convert';

import 'package:client/Models/DatabaseWorld/database_world_model.dart';
import 'package:client/Models/robot/robot_list_view_model.dart';
import 'package:client/Models/robot/robot_model.dart';
import 'package:client/Models/world/world_model.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart';
import 'package:provider/provider.dart';

Future<void> savingWorld(String ip, String port, String worldID) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/admin/save");
  Response res = await post(uri, body: "{\"World\": $worldID}");
  if (res.statusCode == 200) {
    print("World Saved Successfully");
  }
}

Future<void> loadingWorld(String ip, String port, String worldID) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/world/$worldID");
  Response res = await get(uri);
  if (res.statusCode == 200) {
    print("World Loaded Successfully");
  }
}

Future<List<Robot>> getRobots(String ip, String port) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/robots");
  List<Robot> robots = [];
  Response res = await get(uri);
  if (res.statusCode == 200) {
    List<dynamic> body = json.decode(res.body);
    robots = body.map((dynamic item) => Robot.fromJson(item)).toList();
    return robots;
  } else {
    throw "Can't get robots";
  }
}

Future<bool> getRobot(
    String name, String ip, String port, BuildContext context) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/robot/" + name + "/get");
  Response res = await get(uri);
  if (res.statusCode == 200) {
    Robot robot = Robot.fromJson(jsonDecode(res.body));
    Provider.of<RobotListViewModel>(context, listen: false).setRobot(robot);
    return true;
  }
  return false;
}

Future<String?> moveForwardRobot(String ip, String port, String name) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/robot/" + name);
  Response res = await post(uri,
      body: "{\"robot\": \"" +
          name +
          "\",\"command\" : \"forward\", \"arguments\": [\"1\"]}");
  if (res.statusCode == 200) {
    Map<String, dynamic> body = json.decode(res.body);
    if (body["result"] == "OK") {
      Map<String, dynamic> robotState = body["state"];
      String position =
          "${robotState["position"][0]},${robotState["position"][1]}";
      return position;
    }
  }
}

Future<String?> moveBack(String ip, String port, String name) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/robot/" + name);
  Response res = await post(uri,
      body: "{\"robot\": \"" +
          name +
          "\",\"command\" : \"back\", \"arguments\": [\"1\"]}");
  if (res.statusCode == 200) {
    Map<String, dynamic> body = json.decode(res.body);
    if (body["result"] == "OK") {
      Map<String, dynamic> robotState = body["state"];
      String position =
          "${robotState["position"][0]},${robotState["position"][1]}";
      return position;
    }
  }
}

Future<String?> turnLeft(String ip, String port, String name) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/robot/" + name);
  Response res = await post(uri,
      body: "{\"robot\": \"" +
          name +
          "\",\"command\" : \"turn\", \"arguments\": [\"left\"]}");
  if (res.statusCode == 200) {
    Map<String, dynamic> body = json.decode(res.body);
    if (body["result"] == "OK") {
      Map<String, dynamic> robotState = body["state"];
      String direction = robotState["direction"];
      return direction;
    }
  }
}

Future<String?> turnRight(String ip, String port, String name) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/robot/" + name);
  Response res = await post(uri,
      body: "{\"robot\": \"" +
          name +
          "\",\"command\" : \"turn\", \"arguments\": [\"right\"]}");
  if (res.statusCode == 200) {
    Map<String, dynamic> body = json.decode(res.body);
    if (body["result"] == "OK") {
      Map<String, dynamic> robotState = body["state"];
      String direction = robotState["direction"];
      return direction;
    }
  }
}

Future<void> lookAroundRobot(String ip, String port, Robot robot) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/robot/" + robot.name);
  Response res = await post(uri,
      body: "{\"robot\": \"" +
          robot.name +
          "\",\"command\" : \"look\", \"arguments\": []}");
  if (res.statusCode == 200) {
    print("robot look around!");
  }
}

Future<bool> createRobot(
    String name, String ip, String port, BuildContext context) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/robot/" + name);
  Response res = await post(uri,
      body: "{\"robot\": \"" +
          name +
          "\",\"command\" : \"launch\", \"arguments\": [\"sniper\", \"5\", \"5\"]}");
  if (res.statusCode == 200) {
    Map<String, dynamic> body = json.decode(res.body);
    if (body["result"] == "OK") {
      return true;
    }
    return false;
  }
  return false;
}

Future<void> deleteRobot(
    Robot robot, String ip, String port, BuildContext context) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/robots");
  Response res = await post(uri, body: "{\"name\": ${robot.name}}");
  if (res.statusCode == 200) {
    Provider.of<RobotListViewModel>(context, listen: false)
        .removeFromList(robot);
  } else {
    throw "Error removing robot";
  }
}

Future<List<DatabaseWorld>> getWorlds(String ip, String port) async {
  List<DatabaseWorld> worlds = [];
  var uri = Uri.parse("http://" + ip + ":" + port + "/admin/display");
  Response res = await get(uri);
  if (res.statusCode == 200) {
    List<dynamic> body = json.decode(res.body);
    worlds = body.map((dynamic item) => DatabaseWorld.fromJson(item)).toList();
    return worlds;
  } else {
    throw "Can't get Worlds from Database";
  }
}

Future<World> getWorld(String ip, String port) async {
  List<World> world = [];
  var uri = Uri.parse("http://" + ip + ":" + port + "/world");
  Response res = await get(uri);
  if (res.statusCode == 200) {
    List<dynamic> body = json.decode(res.body);
    world = body.map((dynamic item) => World.fromJson(item)).toList();
    return world[0];
  } else {
    throw "Can't get World State";
  }
}

Future<Map<String, dynamic>> launchARobot(
    String ip, String port, String name) async {
  var uri = Uri.parse("http://$ip:$port/robot/$name");
  Response res = await post(uri,
      body: "{\"robot\": \"" +
          name +
          "\",\"command\" : \"launch\", \"arguments\": [\"sniper\", \"5\", \"5\"]}");
  Map<String, dynamic> body = json.decode(res.body);
  if (res.statusCode == 200) {
    if (body["result"] == "OK") {
      return {"launched": true};
    } else if (body["result"] == "ERROR") {
      Map<String, dynamic> data = body["data"];
      return {"launched": false,"message": data["message"]};
    }
  }
  return {"launched": false, "message": body[""]};
}
