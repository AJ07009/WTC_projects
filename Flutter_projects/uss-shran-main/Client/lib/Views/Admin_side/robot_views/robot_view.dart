import 'package:client/Models/robot/robot_list_view_model.dart';
import 'package:client/Widgets/appbar.dart';
import 'package:client/Widgets/robot_list_tile.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
// import 'package:provider/provider.dart';

class RobotListScreen extends StatefulWidget {
  final String ipAddress;
  final String port;
  const RobotListScreen({Key? key, required this.ipAddress, required this.port})
      : super(key: key);

  @override
  _RobotListScreenState createState() => _RobotListScreenState();
}

class _RobotListScreenState extends State<RobotListScreen> {
  @override
  void initState() {
    super.initState();
    Provider.of<RobotListViewModel>(context, listen: false)
        .fetchAllRobots(widget.ipAddress, widget.port);
  }

  // replace with robot view model
  Widget _buildUI(RobotListViewModel robotModel) {
    if (robotModel.robots.isEmpty) {
      return const Align(child: Text("No Robots Found"));
    } else {
      return RobotList(
          robots: robotModel.robots, ip: widget.ipAddress, port: widget.port);
      // return QuoteList(quotes: viewModel.quotes, ip: widget.ipAddress, port: widget.port,);
    }
  }

  @override
  Widget build(BuildContext context) {
    // replace with robot stuff
    RobotListViewModel robotModel = Provider.of<RobotListViewModel>(context);
    // QuoteListViewModel viewModel = Provider.of<QuoteListViewModel>(context);

    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text("Robot"),
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
          // replace with robot thingy
          _buildUI(robotModel)
        ],
      ),
    );
    // body: const Text("Quotes will be displayed here"));
  }
}
