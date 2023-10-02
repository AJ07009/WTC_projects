import 'package:client/Models/robot/robot_list_view_model.dart';
import 'package:client/Models/robot/robot_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';

class DisplayRobot extends StatelessWidget {
  final Robot robot;
  final String ipAddress;
  final String port;

  // final Quote quote;
  const DisplayRobot(
      {Key? key,
      required this.robot,
      required this.ipAddress,
      required this.port})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
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
                      Text(
                        robot.name,
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
                            Text("- " + robot.status,
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
                                "- [" +
                                    robot.getRobotX() +
                                    ", " +
                                    robot.getRobotY() +
                                    "]",
                                style: const TextStyle(
                                    fontSize: 20, color: Colors.white))
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Consumer<RobotListViewModel>(
                builder: (_, model, child) => SizedBox(
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
                      Provider.of<RobotListViewModel>(context, listen: false)
                          .purgeRobot(context, robot, ipAddress, port);
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
