import 'package:client/Models/DatabaseWorld/database_world_view_model.dart';
import 'package:client/Widgets/appbar.dart';
import 'package:client/Widgets/db_list_tile.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class DBWorldsScreen extends StatefulWidget {
  final String ipAddress;
  final String port;
  const DBWorldsScreen({Key? key, required this.ipAddress, required this.port})
      : super(key: key);

  @override
  _DBWorldsScreenState createState() => _DBWorldsScreenState();
}

class _DBWorldsScreenState extends State<DBWorldsScreen> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    Provider.of<DatabaseWorldViewModel>(context, listen: false)
        .getAllWorlds(widget.ipAddress, widget.port);
  }

  Widget _buildUI(DatabaseWorldViewModel dBWorldModel) {
    if (dBWorldModel.worlds.isEmpty) {
      return const Align(child: Text("No Worlds Found"));
    } else {
      return DBListTile(
          worlds: dBWorldModel.worlds, ip: widget.ipAddress, port: widget.port);
    }
  }

  @override
  Widget build(BuildContext context) {
    DatabaseWorldViewModel dbWorldsViewModel =
        Provider.of<DatabaseWorldViewModel>(context);

    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text("Worlds"),
        flexibleSpace: Container(
          decoration: const BoxDecoration(
              gradient: LinearGradient(
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                  colors: <Color>[Colors.pinkAccent, Colors.cyan])),
        ),
      ),
      body: Stack(
        children: [
          _buildUI(dbWorldsViewModel)
        ],
      ),
    );
  }
}
