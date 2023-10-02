import 'dart:convert';
import 'package:http/http.dart';


Future<void> postQuote(String ip, String port,String text, String name) async {
  Uri quoteUrl = Uri.parse("http://"+ip+":"+port+"/quotes");

  Response res;
  if (name.length == 0) {
    res = await post(quoteUrl,
        body: jsonEncode(<String, String>{'text': text, 'name': 'Anonymous'}));
  } else {
    res = await post(quoteUrl,
        body: jsonEncode(<String, String>{
          'text': text,
          'name': name,
        }));
  }
  print(res.body);
  print(res.request);
}
