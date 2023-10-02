import 'dart:convert';
import 'dart:core';

String newQuoteToJson(QuoteList data) => json.encode(data.toJson());

class QuoteList {
  final String text;
  final String name;

  QuoteList({
    required this.text,
    required this.name,
  });

  factory QuoteList.fromJson(Map<String, dynamic> json) {
    return QuoteList(
      text: json['text'],
      name: json['name'],
    );
  }
  Map<String, dynamic> toJson() => {
        "text": text,
        "name": name,
      };
}
