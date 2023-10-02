import 'package:client/Models/DatabaseWorld/database_world_view_model.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';

class DisplayWorldData extends StatelessWidget {
  final DatabaseWorldModel world;
  final String ipAddress;
  final String port;

  // final Quote quote;
  const DisplayWorldData(
      {Key? key,
      required this.world,
      required this.ipAddress,
      required this.port})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    List<String> robStates = world.getRobotStats();
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
      body: Stack(fit: StackFit.expand, children: [
        Center(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Container(
                  margin: const EdgeInsets.symmetric(
                      horizontal: 40.0, vertical: 10.0),
                  padding: const EdgeInsets.all(15.0),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(10.0),
                    shape: BoxShape.rectangle,
                    color: Colors.black.withOpacity(0.5),
                    boxShadow: const <BoxShadow>[
                      BoxShadow(
                        color: Colors.black26,
                        blurRadius: 5.0,
                        offset: Offset(5.0, 5.0),
                      ),
                    ],
                  ),
                  child: Column(
                    children: <Widget>[
                      Text(
                        world.getWorldID(),
                        style: GoogleFonts.lato(
                            textStyle: Theme.of(context).textTheme.headline4,
                            fontSize: 20,
                            fontWeight: FontWeight.w700,
                            fontStyle: FontStyle.italic,
                            color: Colors.white),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Row(
                          children: [
                            Text("Top Left: " + world.getTopPos(),
                                style: const TextStyle(
                                    fontSize: 20, color: Colors.white))
                          ],
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Row(
                          children: [
                            Text("Bottom Right: " + world.getBottomPos(),
                                style: const TextStyle(
                                    fontSize: 20, color: Colors.white))
                          ],
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Row(
                          children: [
                            Text(
                                "Obstacle Count: " +
                                    world.getObstacles().length.toString(),
                                style: const TextStyle(
                                    fontSize: 20, color: Colors.white))
                          ],
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Row(
                          children: const [
                            Text("Robot States:",
                                style: TextStyle(
                                    fontSize: 20, color: Colors.white))
                          ],
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Column(
                          children: [
                            Text("MaxShots - " + robStates[1],
                                style: const TextStyle(
                                    fontSize: 10, color: Colors.white)),
                            Text("MaxShields - " + robStates[0],
                                style: const TextStyle(
                                    fontSize: 10, color: Colors.white)),
                            Text("Reload Time - " + robStates[3] + " Seconds",
                                style: const TextStyle(
                                    fontSize: 10, color: Colors.white)),
                            Text("Repair Time - " + robStates[2] + " Seconds",
                                style: const TextStyle(
                                    fontSize: 10, color: Colors.white)),
                            Text("Set Mine Time - " + robStates[5] + " Seconds",
                                style: const TextStyle(
                                    fontSize: 10, color: Colors.white)),
                            Text("Visibility - " + robStates[4],
                                style: const TextStyle(
                                    fontSize: 10, color: Colors.white)),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Consumer<DatabaseWorldViewModel>(
                builder: (_, model, child) => SizedBox(
                  height: 50,
                  width: 100,
                  child: MaterialButton(
                    color: Colors.lightGreen.withOpacity(0.5),
                    shape: const RoundedRectangleBorder(
                        borderRadius: BorderRadius.all(Radius.circular(20.0))),
                    elevation: 5.0,
                    minWidth: 200.0,
                    height: 35,
                    child: const Text("Load World",
                        style: TextStyle(color: Colors.white, fontSize: 20.0)),
                    onPressed: () {
                      Provider.of<DatabaseWorldViewModel>(context, listen: false).loadWorld(ipAddress, port, world.databaseWorld.worldID);
                      Navigator.pop(context);
                    },
                  ),
                ),
              ),
              SizedBox(
                height: 50,
                width: 100,
                child: MaterialButton(
                  color: Colors.black.withOpacity(0.5),
                  shape: const RoundedRectangleBorder(
                      borderRadius: BorderRadius.all(Radius.circular(20.0))),
                  elevation: 5.0,
                  minWidth: 200.0,
                  height: 35,
                  child: const Text("Close",
                      style: TextStyle(color: Colors.white, fontSize: 20.0)),
                  onPressed: () {
                    Navigator.pop(context);
                    // print(quote);
                    // print(quote.text);
                    // print(quote.name);
                    // print(quote.id);
                  },
                ),
              ),
            ],
          ),
        ),
      ]),
    );
  }
}
