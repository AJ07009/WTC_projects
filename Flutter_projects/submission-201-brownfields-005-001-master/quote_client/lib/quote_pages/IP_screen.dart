import 'package:flutter/material.dart';
import 'package:qute_quote_client/quote_pages/quote_posts.dart';

class IP_screen extends StatefulWidget {
  const IP_screen({Key? key}) : super(key: key);

  @override
  _IP_screenState createState() => _IP_screenState();
}

class _IP_screenState extends State<IP_screen> {

  TextEditingController ip = TextEditingController();
  TextEditingController port = TextEditingController();


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        centerTitle: true,
        title: Text("QuteQuotes"),
      ),
      body: Center(
        child: ListView(
          children: [
            SizedBox(height: 100),
            SizedBox(
              width: 200,
              child: TextFormField(
                keyboardType: TextInputType.multiline,
                minLines: 1,
                decoration: InputDecoration(hintText: "Enter IP Address here"),
                controller: ip,
                validator: (value) {
                  if (value!.isEmpty) {
                    return 'Please enter some text in the form!';
                  } else {
                    return null;
                  }
                },
              ),
            ),
            SizedBox(height: 20),
            SizedBox(
              width: 200,
              child: TextFormField(
                keyboardType: TextInputType.multiline,
                minLines: 1,
                decoration: InputDecoration(hintText: "Enter Port here"),
                controller: port,
                validator: (value) {
                  if (value!.isEmpty) {
                    return 'Please enter some text in the form!';
                  } else {
                    return null;
                  }
                },
              ),
            ),
            SizedBox(height: 100),
            ElevatedButton(
                onPressed: () {
                  print(ip.value.text);
                  print(port.value.text);
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) =>
                              PostsPage(
                                  ip: ip.value.text,
                                  port: port.value.text)));
                },
                child: Text("Submit IP and Port")),
          ],
        ),
      ),
    );
  }
}