import 'package:client/Views/Admin_side/World_views/display_current_world.dart';
import 'package:client/Views/Admin_side/database_views/display_worlds.dart';
import 'package:client/Views/Admin_side/obstacle_views/obstacle_view.dart';
import 'package:client/Views/Admin_side/robot_views/robot_view.dart';
import 'package:client/Widgets/appbar.dart';
import 'package:flutter/material.dart';

class AdminScreen extends StatefulWidget {
  final String ip;
  final String port;
  const AdminScreen({Key? key, required this.ip, required this.port})
      : super(key: key);

  @override
  _AdminScreenState createState() => _AdminScreenState();
}

class _AdminScreenState extends State<AdminScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text("Robot Worlds"),
        flexibleSpace: Container(
          decoration: const BoxDecoration(
              gradient: LinearGradient(
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                  colors: <Color>[Colors.pinkAccent, Colors.cyan])),
        ),
      ),
      body: Stack(
        children: <Widget>[
          Container(
            decoration: const BoxDecoration(
                image: DecorationImage(
                    fit: BoxFit.cover,
                    image: AssetImage('assets/galaxy2.png'))),

          ),
          Center(
            child: SingleChildScrollView(
              padding: const EdgeInsets.all(40.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: <Widget>[
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => RobotListScreen(
                                ipAddress: widget.ip,
                                port: widget.port,
                              )
                          ));
                    },
                    child: const Padding(
                        padding: EdgeInsets.only(top:15.0,bottom: 15.0,left: 15.0),
                        child: Text("List all Robots")),
                    style: ElevatedButton.styleFrom(
                        primary: Colors.pinkAccent,
                        textStyle:
                        const TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold)),

                  ),
                  const SizedBox(
                    height: 20.0,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => ObstacleListScreen(
                                  ipAddress: widget.ip, port: widget.port)
                          ));
                    },
                    child: const Padding(
                        padding: EdgeInsets.only(top:15.0,bottom: 15.0,left: 15.0),
                        child: Text("List all Obstacles")),
                    style: ElevatedButton.styleFrom(
                        primary: Colors.pinkAccent,
                        textStyle:
                        const TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold)),

                  ),
                  const SizedBox(
                    height: 15.0,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => DisplayWorld(
                                  ipAddress: widget.ip, port: widget.port)
                          ));
                    },
                    child: const Padding(
                        padding: EdgeInsets.only(top:15.0,bottom: 15.0,left: 15.0),
                        child: Text("Load World Data")),
                    style: ElevatedButton.styleFrom(
                        primary: Colors.pinkAccent,
                        textStyle:
                        const TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold)),

                  ),
                  const SizedBox(
                    height: 15.0,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => DBWorldsScreen(
                                  ipAddress: widget.ip, port: widget.port)
                          ));
                    },
                    child: const Padding(
                        padding: EdgeInsets.only(top:10.0,bottom: 10.0,left: 15.0),
                        child: Text("Display Worlds from Database")),
                    style: ElevatedButton.styleFrom(
                        primary: Colors.pinkAccent,
                        textStyle:
                        const TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold)),

                  ),
                  const SizedBox(
                    height: 10.0,
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
