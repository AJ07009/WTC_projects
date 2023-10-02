import 'package:flutter/material.dart';
import 'package:qute_quote_client/api_service/quote_model.dart';

class PostDetail extends StatelessWidget {
  final QuoteList posts;

  PostDetail({required this.posts});
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(posts.text),
      ),
      body: SingleChildScrollView(
          child: Padding(
              padding: const EdgeInsets.all(12),
              child: Card(
                  child: Column(
                children: <Widget>[
                  ListTile(
                    title: Text("Quote"),
                    subtitle: Text(posts.text),
                  ),
                  ListTile(
                    title: Text("Author"),
                    subtitle: Text(posts.name),
                  )
                ],
              )))),
    );
  }
}
