import 'package:client/Models/DatabaseWorld/database_world_view_model.dart';
import 'package:client/Models/world/world_view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';

class DisplayWorld extends StatefulWidget {
  final String ipAddress;
  final String port;

  const DisplayWorld({Key? key, required this.ipAddress, required this.port})
      : super(key: key);

  @override
  State<DisplayWorld> createState() => _DisplayWorldState();
}

class _DisplayWorldState extends State<DisplayWorld> {
  @override
  void initState() {
    super.initState();
    Provider.of<WorldViewModel>(context, listen: false)
        .fetchWorldData(widget.ipAddress, widget.port);
  }

  final TextEditingController _worldID = TextEditingController();
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    WorldViewModel worldData = Provider.of<WorldViewModel>(context);
    WorldModel world = worldData.worlds.first;
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text("World Data"),
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
                        "Current World Data"
                            " ",
                        style: GoogleFonts.lato(
                            textStyle: Theme.of(context).textTheme.headline4,
                            fontSize: 25,
                            fontWeight: FontWeight.w700,
                            fontStyle: FontStyle.italic,
                            color: Colors.white),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(10.0),
                        child: Row(
                          children: [
                            Text(
                                "World Top Left: [" + world.world.topLeft + "]",
                                style: const TextStyle(
                                    fontSize: 16, color: Colors.white))
                          ],
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Row(
                          children: [
                            Text(
                                "World Bottom Right: [" +
                                    world.world.bottomRight +
                                    "]",
                                style: const TextStyle(
                                    fontSize: 16, color: Colors.white))
                          ],
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Row(
                          children: [
                            Text(
                                "World Obstacle Count: " +
                                    world.getWorldObs().length.toString(),
                                style: const TextStyle(
                                    fontSize: 16, color: Colors.white))
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Form(
                key: _formKey,
                child: SizedBox(
                    width: MediaQuery.of(context).size.width / 1.4,
                    child: TextFormField(
                      keyboardType: TextInputType.multiline,
                      minLines: 1,
                      maxLines: 3,
                      decoration: const InputDecoration(
                          hintText: "Enter a WorldID here"),
                      controller: _worldID,
                      validator: (value) {
                        if (value!.isEmpty) {
                          return 'Please enter a WorldID!';
                        } else {
                          return null;
                        }
                      },
                    )),
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
                    child: const Text("Save World",
                        style: TextStyle(color: Colors.white, fontSize: 20.0)),
                    onPressed: () {
                      if (_formKey.currentState!.validate()) {
                        Provider.of<DatabaseWorldViewModel>(context,
                                listen: false)
                            .saveWorld(
                                widget.ipAddress, widget.port, _worldID.text);
                        Navigator.pop(context);
                      }
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
