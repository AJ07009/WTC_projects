import 'package:client/Models/robot/robot_list_view_model.dart';
import 'package:client/Views/Admin_side/robot_views/diplay_robot_screen.dart';
import 'package:flutter/material.dart';

// ignore: must_be_immutable
class RobotList extends StatelessWidget {
  late List<RobotViewModel> robots;
  final String ip;
  final String port;

  RobotList(
      {Key? key, required this.robots, required this.ip, required this.port})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: robots.length,
        itemBuilder: (BuildContext context, int index) {
          final robot = robots[index];

          return ListTile(
            title: Text(robot.robotName),
            subtitle: Text(robot.robotStatus),
            onTap: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => DisplayRobot(
                            robot: robot.robot,
                            ipAddress: ip,
                            port: port,
                          )));
            },
          );
        });
  }
}
