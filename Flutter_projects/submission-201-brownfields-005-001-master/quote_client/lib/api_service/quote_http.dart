import 'dart:convert';
import 'package:qute_quote_client/api_service/quote_model.dart';
import 'package:http/http.dart' as http;

class HttpService {
  Future<List<QuoteList>> getQuotes(String ip, String port) async {
    var getUrl = Uri.parse("http://" + ip + ":" + port + "/quotes");
    var response = await http.get(getUrl);
    print('Response status: ${response.statusCode}');

    if (response.statusCode == 200 || response.statusCode == 20) {
      List<dynamic> body = jsonDecode(response.body);

      List<QuoteList> posts =
          body.map((dynamic item) => QuoteList.fromJson(item)).toList();
      print(posts);
      return posts;
    } else {
      throw "What am I looking for here.";
    }
  }
}
