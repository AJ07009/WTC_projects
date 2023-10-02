import 'package:client/Models/osbstacles/obstacle_list_view_model.dart';
import 'package:client/Views/Admin_side/obstacle_views/display_obstacle_screen.dart';
import 'package:flutter/material.dart';

// ignore: must_be_immutable
class ObstacleListTile extends StatelessWidget {
  late List<ObstacleViewModel> obs;
  final String ipAddress;
  final String port;
  ObstacleListTile(
      {Key? key,
      required this.obs,
      required this.ipAddress,
      required this.port})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: obs.length,
        itemBuilder: (BuildContext context, int index) {
          final obstacle = obs[index];

          return ListTile(
            title: const Text("Obstacle"),
            subtitle: const Text("click for more info"),
            onTap: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => DisplayObstacle(
                            obs: obstacle.obs,
                            ipAddress: ipAddress,
                            port: port,
                          )));
            },
          );
        });
  }
}
