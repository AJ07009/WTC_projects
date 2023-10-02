import 'package:client/Models/osbstacles/obstacle_list_view_model.dart';
import 'package:client/Models/osbstacles/obstacle_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class CreateObs extends StatefulWidget {
  final String ipAddress;
  final String port;
  const CreateObs({Key? key, required this.ipAddress, required this.port})
      : super(key: key);

  @override
  _CreateObsState createState() => _CreateObsState();
}

class _CreateObsState extends State<CreateObs> {
  final TextEditingController _obsPosY = TextEditingController();
  final TextEditingController _obsPosX = TextEditingController();
  late ObstacleList _obs;
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Dialog(
      child: Form(
        key: _formKey,
        child: SizedBox(
          height: 400,
          width: 400,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    const Text("Add Obstacle", style: TextStyle(fontSize: 20)),
                    SizedBox(width: MediaQuery.of(context).size.width / 6),
                    IconButton(
                        icon: const Icon(Icons.clear),
                        onPressed: () {
                          Navigator.of(context).pop();
                        })
                  ],
                ),
              ),
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        SizedBox(
                          width: MediaQuery.of(context).size.width / 1.4,
                          child: TextFormField(
                            keyboardType: TextInputType.multiline,
                            minLines: 1,
                            maxLines: 3,
                            decoration: const InputDecoration(
                                hintText: "Enter obstacle X position"),
                            controller: _obsPosX,
                            validator: (value) {
                              if (value!.isEmpty) {
                                return 'Please enter the position!';
                              } else {
                                return null;
                              }
                            },
                          ),
                        )
                      ],
                    ),
                  )
                ],
              ),
              const SizedBox(height: 40),
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        SizedBox(
                          width: MediaQuery.of(context).size.width / 1.4,
                          child: TextFormField(
                            keyboardType: TextInputType.multiline,
                            minLines: 1,
                            maxLines: 2,
                            decoration: const InputDecoration(
                                hintText: "Enter obstacle Y position, pleth"),
                            controller: _obsPosY,
                            validator: (value) {
                              if (value!.isEmpty) {
                                return 'Please enter the authors name!';
                              } else {
                                return null;
                              }
                            },
                          ),
                        )
                      ],
                    ),
                  )
                ],
              ),
              ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      _obs = ObstacleList(
                          bottomLeftX: int.parse(_obsPosX.value.text),
                          bottomLeftY: int.parse(_obsPosY.value.text));
                      Provider.of<ObstacleListViewModel>(context, listen: false)
                          .addingObstacle(
                        context,
                        widget.ipAddress,
                        widget.port,
                        _obs,
                      );
                      Navigator.pop(context);
                      // print("Submitting quote");
                    }
                  },
                  child: const Text("Submit")),
            ],
          ),
        ),
      ),
    );
  }
}
