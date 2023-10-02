// ignore_for_file: must_be_immutable

import 'package:client/Models/DatabaseWorld/database_world_view_model.dart';
import 'package:client/Views/Admin_side/database_views/display_world_data.dart';
import 'package:flutter/material.dart';

class DBListTile extends StatelessWidget {
  late List<DatabaseWorldModel> worlds;
  final String ip;
  final String port;

  DBListTile(
      {Key? key, required this.worlds, required this.ip, required this.port})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: worlds.length,
        itemBuilder: (BuildContext context, int index) {
          final world = worlds[index];

          return ListTile(
            title: Text(world.getWorldID()),
            subtitle: const Text("Press to view World Data"),
            onTap: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => DisplayWorldData(
                            world: world,
                            ipAddress: ip,
                            port: port,
                          )));
            },
          );
        });
  }
}
