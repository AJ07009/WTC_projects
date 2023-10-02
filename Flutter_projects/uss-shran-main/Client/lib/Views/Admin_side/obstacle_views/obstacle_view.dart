import 'package:client/Models/osbstacles/obstacle_list_view_model.dart';
import 'package:client/Widgets/appbar.dart';
import 'package:client/Widgets/obstacle_dialog.dart';
import 'package:client/Widgets/obstacle_list_tile.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
// import 'package:provider/provider.dart';

class ObstacleListScreen extends StatefulWidget {
  final String ipAddress;
  final String port;
  const ObstacleListScreen(
      {Key? key, required this.ipAddress, required this.port})
      : super(key: key);

  @override
  _ObstacleListScreenState createState() => _ObstacleListScreenState();
}

class _ObstacleListScreenState extends State<ObstacleListScreen> {
  @override
  void initState() {
    super.initState();
    Provider.of<ObstacleListViewModel>(context, listen: false)
        .fetchAllObstacles(widget.ipAddress, widget.port);
  }

  Widget _buildUI(ObstacleListViewModel viewModel) {
    if (viewModel.obsList.isEmpty) {
      return const Text("There are no Obstacles");
    } else {
      return ObstacleListTile(
          obs: viewModel.obsList,
          ipAddress: widget.ipAddress,
          port: widget.port);
    }
  }

  @override
  Widget build(BuildContext context) {
    ObstacleListViewModel obData = Provider.of<ObstacleListViewModel>(context);

    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text("Obstacle"),
        flexibleSpace: Container(
          decoration: const BoxDecoration(
              gradient: LinearGradient(
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                  colors: <Color>[Colors.pinkAccent, Colors.cyan])),
        ),
      ),
      // body: _buildUI(viewModel),
      body: Stack(
        children: [
          // replace with robot thingy
          _buildUI(obData)
        ],
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.plus_one),
        onPressed: () {
          showDialog(
              context: context,
              builder: (BuildContext build) =>
                  CreateObs(ipAddress: widget.ipAddress, port: widget.port));
        },
      ),
    );
    // body: const Text("Quotes will be displayed here"));
  }
}
