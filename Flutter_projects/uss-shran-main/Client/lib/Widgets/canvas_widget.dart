//TODO: Implement to Player Screen
//TODO: Write test for canvas
import 'package:client/Models/osbstacles/obstacle_list_view_model.dart';
import 'package:client/Models/robot/robot_list_view_model.dart';
import 'package:client/Models/robot/robot_model.dart';
import 'package:client/Models/world/world_view_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
// world provider thingy
//put obstacle list thingy here
// obs model
// robot model

class CanvasWidget extends StatefulWidget {
  final Robot robot;
  final String ipAddress;
  final String port;
  const CanvasWidget(
      {Key? key,
      required this.robot,
      required this.ipAddress,
      required this.port})
      : super(key: key);

  @override
  _CanvasWidgetState createState() => _CanvasWidgetState();
}

class _CanvasWidgetState extends State<CanvasWidget> {
  @override
  void initState() {
    super.initState();
    Provider.of<ObstacleListViewModel>(context, listen: false)
        .fetchAllObstacles(widget.ipAddress, widget.port);
    Provider.of<RobotListViewModel>(context, listen: false)
        .fetchAllRobots(widget.ipAddress, widget.port);
    Provider.of<WorldViewModel>(context, listen: false)
        .fetchWorldData(widget.ipAddress, widget.port);
  }

  @override
  Widget build(BuildContext context) {
    RobotListViewModel robotModel = Provider.of<RobotListViewModel>(context);
    ObstacleListViewModel obData = Provider.of<ObstacleListViewModel>(context);
    WorldViewModel worldData = Provider.of<WorldViewModel>(context);
    WorldModel world = worldData.worlds.first;
    // print(world);
    // print("Robot pos: " + widget.robot.position);

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme:
          ThemeData.dark().copyWith(appBarTheme: Theme.of(context).appBarTheme),
      home: Scaffold(
        // Outer white container with padding
        body: Container(
          // padding: EdgeInsets.symmetric(horizontal: 40, vertical: 80),
          color: Colors.transparent,
          // Inner yellow container
          child: Container(
            color: Colors.transparent,
            child: LayoutBuilder(
              // Inner yellow container
              builder: (_, constraints) => Container(
                width: constraints.widthConstraints().maxWidth,
                height: constraints.heightConstraints().maxHeight,
                color: Colors.transparent,
                child: CustomPaint(
                    painter: ObstaclePainter(
                        world, obData.obsList, widget.robot, robotModel)),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class ObstaclePainter extends CustomPainter {
  WorldModel world;
  List<ObstacleViewModel> obstacles;
  Robot robot;
  RobotListViewModel robots;

  ObstaclePainter(this.world, this.obstacles, this.robot, this.robots);

  @override
  void paint(Canvas canvas, Size size) {
    final paint = Paint()
      ..style = PaintingStyle.stroke
      ..strokeWidth = 2.0
      ..color = Colors.indigo;
    // print(world.world.bottomRight);
    // print(world.world.topLeft);
    final xSplitPos = world.world.bottomRight.split(',');
    List xValue = [];
    for (int i = 0; i < xSplitPos.length; i++) {
      xValue.add(xSplitPos[i]);
    }
    final ySplitPos = world.world.topLeft.split(',');
    List yValue = [];
    for (int i = 0; i < ySplitPos.length; i++) {
      yValue.add(ySplitPos[i]);
    }
    int worldHeight = int.parse(xValue[0]) * 2;
    int worldWidth = int.parse(yValue[1]) * 2;
    // int worldHeight = 40;
    // int worldWidth = 40;
    // print("WH: " + worldHeight.toString());
    // print("WW: " + worldWidth.toString());
    double cellSizeW = 350 / worldWidth.toDouble();
    double cellSizeH = 350 / worldHeight.toDouble();
    Offset center = Offset(size.width / 2, size.height / 2);
    paint.style = PaintingStyle.fill;
    for (int i = 0; i < obstacles.length; i++) {
      canvas.drawRect(
        Rect.fromLTWH(
            (center.dx -
                (cellSizeW / 2) +
                (obstacles[i].obs.bottomLeftX.toDouble() * cellSizeW)),
            (center.dy -
                (cellSizeH / 2) -
                (obstacles[i].obs.bottomLeftY.toDouble() * cellSizeH)),
            cellSizeW,
            cellSizeH),
        paint,
      );
    }

    Offset robotCentre = Offset(
        center.dx + (int.parse(robot.getRobotX()).toDouble() * cellSizeW),
        center.dy - (int.parse(robot.getRobotY()).toDouble() * cellSizeH));
    paint.color = Colors.green;
    // canvas.drawImage(image, offset, paint)
    canvas.drawCircle(robotCentre, cellSizeW / 2, paint);

    for (int j = 0; j < robots.robots.length; j++) {
      if (robots.robots[j].robot.name != robot.name) {
        Offset otherRobotCentre = Offset(
            center.dx +
                (int.parse(robots.robots[j].robot.getRobotX()) * cellSizeW),
            center.dy -
                (int.parse(robots.robots[j].robot.getRobotY()) * cellSizeH));

        paint.color = Colors.red;
        canvas.drawCircle(otherRobotCentre, cellSizeH / 2, paint);
      }
    }
  }

  @override
  bool shouldRepaint(ObstaclePainter oldDelegate) => false;
}
