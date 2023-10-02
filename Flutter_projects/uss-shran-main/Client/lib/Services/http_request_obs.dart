import 'dart:convert';

import 'package:client/Models/osbstacles/obstacle_list_view_model.dart';
import 'package:client/Models/osbstacles/obstacle_model.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart';
import 'package:provider/provider.dart';

Future<List<ObstacleList>> getObstacles(String ip, String port) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/admin/obstacles");
  List<ObstacleList> obstacles = [];
  Response res = await get(uri);
  if (res.statusCode == 200) {
    List<dynamic> body = json.decode(res.body);
    obstacles =
        body.map((dynamic item) => ObstacleList.fromJson(item)).toList();
    return obstacles;
  } else {
    throw "Can't get robots";
  }
}

Future<void> addObstacle(
    BuildContext context, String ip, String port, ObstacleList obstacle) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/admin/obstacles/add");
  Response res = await post(uri,
      body: "{\"Obstacle\": "
          "[${obstacle.bottomLeftX}, ${obstacle.bottomLeftY}]}");
  if (res.statusCode == 200 || res.statusCode == 201) {
    Provider.of<ObstacleListViewModel>(context, listen: false)
        .addToObsList(obstacle);
  }
}

Future<void> removeObstacle(
    BuildContext context, String ip, String port, ObstacleList obstacle) async {
  var uri = Uri.parse("http://" + ip + ":" + port + "/admin/obstacles/remove");
  Response res = await post(uri,
      body: "{\"Obstacle\": "
          "[${obstacle.bottomLeftX}, ${obstacle.bottomLeftY}]}");
  if (res.statusCode == 200 || res.statusCode == 201) {
    Provider.of<ObstacleListViewModel>(context, listen: false)
        .removeFromObsList(obstacle);
  }
}

Map toMap(String bottomLeftX, String bottomLeftY) {
  var map = {};
  map["bottomLeftX"] = bottomLeftX;
  map["bottomLeftY"] = bottomLeftY;

  return map;
}
