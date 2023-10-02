import 'package:client/Models/robot/robot_list_view_model.dart';
import 'package:client/Models/world/world_view_model.dart';
import 'package:client/Services/http_requests.dart';
import 'package:client/Views/Player_Side/game_screen.dart';
import 'package:client/Widgets/appbar.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class PlayerScreen extends StatefulWidget {
  final String ipAddress;
  final String port;
  const PlayerScreen({Key? key, required this.ipAddress, required this.port})
      : super(key: key);

  @override
  _PlayerScreenState createState() => _PlayerScreenState();
}

class _PlayerScreenState extends State<PlayerScreen> {
  @override
  void initState() {
    super.initState();
    Provider.of<RobotListViewModel>(context, listen: false)
        .fetchAllRobots(widget.ipAddress, widget.port);
  }

  bool isLaunching = false;
  TextEditingController name = TextEditingController();
  String selectedValue = "Sniper";

  List<DropdownMenuItem<String>> get typesOfRobots {
    List<DropdownMenuItem<String>> robotTypes = [
      DropdownMenuItem(
          child: Row(
            children: const [
              Image(
                image: AssetImage("assets/sniper.png"),
                height: 40,
                width: 40,
              ),
              Text("Sniper"),
            ],
          ),
          value: "Sniper"),
      DropdownMenuItem(
          child: Row(
            children: const [
              Padding(
                padding: EdgeInsets.only(right: 8.0),
                child: Image(
                  image: AssetImage("assets/attacker.png"),
                  height: 30,
                  width: 30,
                ),
              ),
              Text("Attacker"),
            ],
          ),
          value: "Attacker"),
      DropdownMenuItem(
          child: Row(
            children: const [
              Padding(
                padding: EdgeInsets.only(right: 8.0),
                child: Image(
                  image: AssetImage("assets/allround.png"),
                  height: 30,
                  width: 30,
                ),
              ),
              Text("All rounder"),
            ],
          ),
          value: "All rounder"),
    ];
    return robotTypes;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: const AppBarCustom(
          title: 'Robot Worlds - Player',
          back: false,
        ),
        body: Container(
          decoration: const BoxDecoration(
            image: DecorationImage(
              image: AssetImage("assets/galaxy2.jpg"),
              fit: BoxFit.cover,
            ),
          ),
          child: Center(
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Expanded(
                    child: Image.asset("assets/fighterbot.gif",
                        fit: BoxFit.contain),
                  ),
                  const Text(
                    "Launch a robot into the game",
                    style: TextStyle(
                        color: Colors.white,
                        fontWeight: FontWeight.bold,
                        fontSize: 20),
                  ),
                  Container(
                    padding: const EdgeInsets.all(8),
                    width: 200,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10.0),
                      color: Colors.white70,
                      border: Border.all(),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.only(bottom: 8.0, top: 8.0),
                      child: ConstrainedBox(
                        constraints:
                            const BoxConstraints(minWidth: 200, maxWidth: 250),
                        child: TextField(
                            controller: name,
                            decoration: const InputDecoration(
                              fillColor: Colors.white70,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                      color: Colors.white, width: 2)),
                              filled: true,
                              border: OutlineInputBorder(),
                              labelStyle:
                                  TextStyle(color: Colors.black, fontSize: 24),
                              labelText: 'Player Name',
                              hintText: 'Enter a name for your robot',
                              enabledBorder: OutlineInputBorder(
                                  borderSide: BorderSide(
                                      color: Colors.white, width: 2)),
                            )),
                      ),
                    ),
                  ),
                  ElevatedButton(
                      onPressed: () async {
                        _lauchRobot(name.text, selectedValue);
                      },
                      child: const Text("Launch"))
                ],
              ),
            ),
          ),
        ));
  }

  void _lauchRobot(String name, String selectedValue) async {
    await Provider.of<WorldViewModel>(context, listen: false)
        .fetchWorldData(widget.ipAddress, widget.port);

    await launchARobot(widget.ipAddress, widget.port, name).then((value) async {
      if (value["launched"] != false) {
        await getRobot(name, widget.ipAddress, widget.port, context)
            .then((value) {
          if (value != false) {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (_) => GameScreen(
                  name: name,
                  ip: widget.ipAddress,
                  port: widget.port,
                ),
              ),
            );
          }
        });
      } else if (value["launched"] == false) {
        ScaffoldMessenger.of(context)
            .showSnackBar(SnackBar(content: Text(value["message"])));
      }
    });
  }
}
