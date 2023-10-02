import 'package:client/Views/Player_Side/player_sceen.dart';
import 'package:flutter/material.dart';

import 'Admin_side/admin_screen.dart';

class StartingPageWidget extends StatefulWidget {
  final String ip;
  final String port;
  const StartingPageWidget({Key? key, required this.ip, required this.port})
      : super(key: key);

  @override
  _StartingPageWidgetState createState() => _StartingPageWidgetState();
}

class _StartingPageWidgetState extends State<StartingPageWidget> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
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
                              builder: (context) => AdminScreen(
                                    ip: widget.ip,
                                    port: widget.port,
                                  )));
                    },
                    child: const Padding(
                        padding: EdgeInsets.only(
                            top: 20.0, bottom: 20.0, left: 15.0),
                        child: Text("Admin")),
                    style: ElevatedButton.styleFrom(
                        primary: Colors.pinkAccent,
                        textStyle: const TextStyle(
                            fontSize: 40, fontWeight: FontWeight.bold)),
                  ),
                  const SizedBox(
                    height: 20.0,
                  ),
                  const SizedBox(
                    height: 10.0,
                  ),
                  const SizedBox(
                    height: 15.0,
                  ),
                  const SizedBox(
                    height: 70.0,
                  ),
                  const SizedBox(
                    height: 10.0,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => PlayerScreen(
                                    ipAddress: widget.ip,
                                    port: widget.port,
                                  )));
                    },
                    child: const Padding(
                        padding: EdgeInsets.only(
                            top: 20.0, bottom: 20.0, left: 15.0),
                        child: Text("Player")),
                    style: ElevatedButton.styleFrom(
                        primary: Colors.lightBlueAccent,
                        textStyle: const TextStyle(
                            fontSize: 40, fontWeight: FontWeight.bold)),
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
