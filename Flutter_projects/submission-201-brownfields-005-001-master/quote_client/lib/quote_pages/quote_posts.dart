import 'package:flutter/material.dart';
import 'package:qute_quote_client/quote_pages/post_detail.dart';
import 'package:qute_quote_client/api_service/quote_http.dart';
import 'package:qute_quote_client/api_service/quote_model.dart';
import 'package:qute_quote_client/quote_pages/widget.dart';

class PostsPage extends StatefulWidget {
  final String ip;
  final String port;

  const PostsPage({Key? key, required this.ip, required this.port})
      : super(key: key);
  @override
  State<PostsPage> createState() => _PostsPageState();
}

class _PostsPageState extends State<PostsPage> {
  final HttpService httpService = HttpService();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text("Quotes"),
        flexibleSpace: Container(
          decoration: BoxDecoration(
              gradient: LinearGradient(
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                  colors: <Color>[Colors.pinkAccent, Colors.cyan])),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => showDialog(
            context: context,
            builder: (BuildContext build) =>
                QuoteScreen(ip: widget.ip, port: widget.port)),
        child: Icon(Icons.add),
      ),
      body: FutureBuilder(
          future: httpService.getQuotes(widget.ip, widget.port),
          builder:
              (BuildContext context, AsyncSnapshot<List<QuoteList>> snapshot) {
            if (snapshot.hasData) {
              List<QuoteList> data = snapshot.data!;
              return ListView(
                padding: EdgeInsets.all(10),
                children: data
                    .map(
                      (QuoteList posts) => ListTile(
                        hoverColor: Colors.white,
                        title: Text((posts.text),
                            style: const TextStyle(
                              fontSize: 18,
                              color: Colors.cyan,
                              fontWeight: FontWeight.bold,
                            )),
                        subtitle: Text(
                          posts.name.toString(),
                          style: const TextStyle(
                            fontSize: 16,
                            color: Colors.amberAccent,
                          ),
                        ),
                        onTap: () =>
                            Navigator.of(context).push(MaterialPageRoute(
                                builder: (context) => PostDetail(
                                      posts: posts,
                                    ))),
                      ),
                    )
                    .toList(),
              );
            }
            return Center(child: CircularProgressIndicator());
          }),
    );
  }
}
