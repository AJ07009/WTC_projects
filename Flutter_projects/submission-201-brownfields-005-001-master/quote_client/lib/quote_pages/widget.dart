import 'package:flutter/material.dart';
import 'package:qute_quote_client/api_service/quote_http_post.dart';
import 'package:qute_quote_client/quote_pages/quote_posts.dart';

class QuoteScreen extends StatefulWidget {
  final String ip;
  final String port;

  const QuoteScreen({Key? key, required this.ip, required this.port})
      : super(key: key);

  @override
  _QuoteScreenState createState() => _QuoteScreenState();
}

class _QuoteScreenState extends State<QuoteScreen> {
  final TextEditingController quoteInput = TextEditingController();
  final TextEditingController authorInput = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Dialog(
        child: Container(
      child: Column(
        children: [
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 8, vertical: 16),
            child: TextField(
              controller: quoteInput,
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                hintText: 'Enter a quote pleth',
              ),
            ),
          ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 8, vertical: 16),
            child: TextField(
              controller: authorInput,
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                hintText: 'Enter a author pleth',
              ),
            ),
          ),
          SizedBox(
            height: 50,
            width: 50,
          ),
          ElevatedButton(
            onPressed: () async {
              final String text = quoteInput.text;
              final String name = authorInput.text;
              await postQuote(widget.ip, widget.port, text, name);
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) =>
                          PostsPage(ip: widget.ip, port: widget.port)));
            },
            child: Icon(Icons.add),
          )
        ],
      ),
    ));
  }
}
