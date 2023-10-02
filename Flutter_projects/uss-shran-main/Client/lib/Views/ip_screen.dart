import 'package:client/Views/start_screen.dart';
import 'package:flutter/material.dart';

class IPInput extends StatefulWidget {
  const IPInput({Key? key}) : super(key: key);

  @override
  _IPInputState createState() => _IPInputState();
}

class _IPInputState extends State<IPInput> {

  final TextEditingController _controller = TextEditingController();
  final TextEditingController _controllerPort = TextEditingController();

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
              padding: const EdgeInsets.all(30.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: <Widget>[
                  CircleAvatar(

                    radius: 90.0,
                    child: Image.asset('assets/app_icon.png'),
                  ),
                  const SizedBox(
                    height: 20.0,
                  ),
                  TextFormField(
                    style: const TextStyle(
                        color: Colors.white
                    ),
                    decoration: const InputDecoration(
                        hintStyle: TextStyle(color: Colors.white),
                        filled: true,
                        fillColor: Colors.black45,
                        hintText: 'Enter IP Address'),
                    controller: _controller,
                    validator: (value) {
                      if (value!.isEmpty) {
                        return 'Please enter some text in the form!';
                      } else {
                        return null;
                      }
                    },
                  ),
                  const SizedBox(
                    height: 10.0,
                  ),
                  TextFormField(
                    decoration: const InputDecoration(
                        hintStyle: TextStyle(color: Colors.white),
                        filled: true,
                        fillColor: Colors.black45,
                        hintText: 'Enter Port number'),
                    controller: _controllerPort,
                    validator: (value) {
                      if (value!.isEmpty) {
                        return 'Please enter some text in the form!';
                      } else {
                        return null;
                      }
                    },
                  ),
                  const SizedBox(
                    height: 15.0,
                  ),
                  const SizedBox(
                    height: 15.0,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => StartingPageWidget(
                                  ip: _controller.value.text,
                                  port: _controllerPort.value.text)));
                    },
                    child: const Padding(
                        padding: EdgeInsets.only(top:15.0,bottom: 15.0,left: 15.0),
                        child: Text("Submit IP and Port")),
                    style: ElevatedButton.styleFrom(
                        primary: Colors.lightBlueAccent,
                        textStyle:
                        const TextStyle(
                            fontSize: 16, fontWeight: FontWeight.bold)),

                  ),
                  const SizedBox(
                    height: 10.0,
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
