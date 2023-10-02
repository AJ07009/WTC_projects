import 'package:client/Models/osbstacles/obstacle_list_view_model.dart';
import 'package:client/Models/osbstacles/obstacle_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';

class DisplayObstacle extends StatelessWidget {
  final String ipAddress;
  final String port;
  final ObstacleList obs;
  // final Quote quote;
  const DisplayObstacle(
      {Key? key,
      required this.obs,
      required this.ipAddress,
      required this.port})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
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
                    //The line below curves the corners of the box
                    borderRadius: BorderRadius.circular(10.0),
                    // The line below is for rectangular shape
                    shape: BoxShape.rectangle,
                    // Below is just the colour of the box with an opacity added.
                    //you can change opacity with color here(I used black) for the box
                    color: Colors.black.withOpacity(0.5),
                    // below is just to adda shadow to the box
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
                      // Text("\"",
                      //     style: GoogleFonts.lato(
                      //         textStyle: Theme.of(context).textTheme.headline4,
                      //         fontSize: 75,
                      //         fontWeight: FontWeight.w700,
                      //         color: Colors.white
                      //     )),
                      Text(
                        "Obstacle",
                        // style: const TextStyle(fontSize: 20)
                        style: GoogleFonts.lato(
                            textStyle: Theme.of(context).textTheme.headline4,
                            fontSize: 30,
                            fontWeight: FontWeight.w700,
                            fontStyle: FontStyle.italic,
                            color: Colors.white),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Row(
                          children: [
                            Text(
                                "Position: [" +
                                    (obs.bottomLeftX.toString() +
                                        ", " +
                                        (obs.bottomLeftY.toString()) +
                                        "]"),
                                style: const TextStyle(
                                    fontSize: 20, color: Colors.white))
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              SizedBox(
                height: 50,
                width: 100,
                child: MaterialButton(
                  color: Colors.red.withOpacity(0.5),
                  shape: const RoundedRectangleBorder(
                      borderRadius: BorderRadius.all(Radius.circular(20.0))),
                  elevation: 5.0,
                  minWidth: 200.0,
                  height: 35,
                  child: const Text("Delete",
                      style: TextStyle(color: Colors.white, fontSize: 20.0)),
                  onPressed: () {
                    Provider.of<ObstacleListViewModel>(context, listen: false)
                        .removingObstacle(context, obs, ipAddress, port);
                    Navigator.pop(context);
                  },
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
