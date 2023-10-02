import 'package:client/Services/http_request_obs.dart';
import 'package:flutter/material.dart';

import 'obstacle_model.dart';

class ObstacleListViewModel extends ChangeNotifier {
  late List<ObstacleViewModel> obsList = [];

  Future<void> fetchAllObstacles(String ipAddress, String port) async {
    final obs = await getObstacles(ipAddress, port);
    obsList =
        obs.map((newcastList) => ObstacleViewModel(obs: newcastList)).toList();
    notifyListeners();
  }

  Future<void> addingObstacle(BuildContext context, String ipAddress,
      String port, ObstacleList obstacleList) async {
    await addObstacle(context, ipAddress, port, obstacleList);
    notifyListeners();
  }

  Future<void> removingObstacle(BuildContext context, ObstacleList obstacleList,
      String ipAddress, String port) async {
    await removeObstacle(context, ipAddress, port, obstacleList);
    notifyListeners();
  }

  void addToObsList(ObstacleList obstacleList) {
    obsList.add(ObstacleViewModel(obs: obstacleList));
    notifyListeners();
  }

  void removeFromObsList(ObstacleList obstacleList) {
    obsList.removeWhere((element) => element.obs == obstacleList);
    notifyListeners();
  }
}

class ObstacleViewModel {
  late final ObstacleList obs;

  ObstacleViewModel({required this.obs});

  String get obsX {
    return obs.bottomLeftX.toString();
  }

  String get obsY {
    return obs.bottomLeftY.toString();
  }
}
