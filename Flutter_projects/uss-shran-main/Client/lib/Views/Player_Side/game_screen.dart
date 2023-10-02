import 'package:client/Models/robot/robot_list_view_model.dart';
import 'package:client/Models/world/world_view_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class GameScreen extends StatefulWidget {
  final String name;
  final String ip;
  final String port;
  const GameScreen(
      {Key? key, required this.name, required this.ip, required this.port})
      : super(key: key);

  @override
  _GameScreenState createState() => _GameScreenState();
}

class _GameScreenState extends State<GameScreen> {
  @override
  void initState() {
    super.initState();
    Provider.of<RobotListViewModel>(context, listen: false)
        .fetchAllRobots(widget.ip, widget.port);
  }

  @override
  Widget build(BuildContext context) {
    final robotListView = Provider.of<RobotListViewModel>(context);
    final worldView = Provider.of<WorldViewModel>(context);
    int numOfCols = worldView.getNumOfBlocksPerRow();
    int numOfRows = worldView.getNumOfRows();

    return Scaffold(
      backgroundColor: Colors.grey[800],
      resizeToAvoidBottomInset: false,
      appBar: playerAppBar(robotListView, worldView),
      body: Column(
        children: [
          Expanded(
              flex: 2,
              child: AspectRatio(
                aspectRatio: numOfCols / numOfRows,
                child: gameGrid(robotListView, numOfCols, numOfRows, worldView),
              )),
          Expanded(
              child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ElevatedButton(
                  onPressed: () {
                    robotListView.forward(widget.ip, widget.port);
                  },
                  style: ElevatedButton.styleFrom(
                    shape: const CircleBorder(),
                    padding: const EdgeInsets.all(20),
                  ),
                  child: const Icon(Icons.arrow_upward_rounded)),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  ElevatedButton(
                      onPressed: () {
                        robotListView.left(widget.ip, widget.port);
                      },
                      style: ElevatedButton.styleFrom(
                        shape: const CircleBorder(),
                        padding: const EdgeInsets.all(20),
                      ),
                      child: const Icon(Icons.arrow_back_rounded)),
                  ElevatedButton(
                      onPressed: () {
                        robotListView.look(widget.ip, widget.port);
                      },
                      style: ElevatedButton.styleFrom(
                        shape: const CircleBorder(),
                        padding: const EdgeInsets.all(20),
                      ),
                      child: const Icon(Icons.remove_red_eye)),
                  ElevatedButton(
                      onPressed: () {
                        robotListView.right(widget.ip, widget.port);
                      },
                      style: ElevatedButton.styleFrom(
                        shape: const CircleBorder(),
                        padding: const EdgeInsets.all(20),
                      ),
                      child: const Icon(Icons.arrow_forward_rounded)),
                ],
              ),
              ElevatedButton(
                  onPressed: () {
                    robotListView.back(widget.ip, widget.port);
                  },
                  style: ElevatedButton.styleFrom(
                    shape: const CircleBorder(),
                    padding: const EdgeInsets.all(20),
                  ),
                  child: const Icon(Icons.arrow_downward_rounded)),
            ],
          ))
        ],
      ),
    );
  }

  GridView gameGrid(RobotListViewModel robotListViewModel, int numOfCols,
      int numOfRows, WorldViewModel worldView) {
    List<String> coords = createCordsList(numOfCols, numOfRows);
    return GridView.builder(
        itemCount: numOfRows * numOfCols,
        physics: const NeverScrollableScrollPhysics(),
        gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: numOfCols),
        itemBuilder: (BuildContext context, int index) {
          return ClipRRect(
            borderRadius: BorderRadius.circular(8),
            child: Container(
              margin: const EdgeInsets.all(1),
              decoration: BoxDecoration(
                color:
                    _blockColor(robotListViewModel, index, coords, worldView),
              ),
            ),
          );
        });
  }

  List<String> createCordsList(int numOfCols, int numOfRows) {
    List<String> coords = [];
    for (int y = (numOfRows / 2).floor();
        y >= ((numOfRows / 2).floor() * -1);
        y--) {
      for (int x = ((numOfCols / 2).floor() * -1).round();
          x < (numOfCols / 2).floor() + 1;
          x++) {
        coords.add("$x,$y");
      }
    }
    return coords;
  }

  Color _blockColor(RobotListViewModel robotListView, int index,
      List<String> coords, WorldViewModel worldView) {
    if (coords[index] == robotListView.robot.position) {
      return Colors.green;
    }
    for (var item in worldView.worlds.first.getWorldObs()) {
      if (coords[index] == "${item["bottomLeftX"]},${item["bottomLeftY"]}") {
        return Colors.red;
      }
    }

    for (var rob in robotListView.robots) {
      if (coords[index] == rob.robot.position &&
          rob.robotName != robotListView.robot.name) {
        return Colors.orange;
      }
    }

    return Colors.grey;
  }

  AppBar playerAppBar(
      RobotListViewModel robotListView, WorldViewModel worldView) {
    return AppBar(
      automaticallyImplyLeading: false,
      title: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Text(robotListView.robot.direction),
          Text(robotListView.robot.position)
        ],
      ),
    );
  }
}
