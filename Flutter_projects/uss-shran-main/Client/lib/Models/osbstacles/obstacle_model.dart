import 'dart:core';

class ObstacleList {
  late int bottomLeftX;
  late int bottomLeftY;

  String getbotX() {
    return bottomLeftX.toString();
  }

  void setbotX(int newBotX) {
    bottomLeftX = newBotX;
  }

  String getbotY() {
    return bottomLeftY.toString();
  }

  void setBotY(int newBotY) {
    bottomLeftY = newBotY;
  }

  ObstacleList create(int newBotX, int newBotY) {
    ObstacleList newscastList = ObstacleList(
      bottomLeftX: 0,
      bottomLeftY: 0,
    );
    newscastList.setbotX(newBotX);
    newscastList.setBotY(newBotY);
    return newscastList;
  }

  ObstacleList({
    required this.bottomLeftX,
    required this.bottomLeftY,
  });

  factory ObstacleList.fromJson(Map<String, dynamic> json) {
    return ObstacleList(
      bottomLeftX: json["bottomLeftX"],
      bottomLeftY: json["bottomLeftY"],
    );
  }
  Map<String, dynamic> toJson() => {
        "bottomLeftY": bottomLeftY,
        "bottomLeftX": bottomLeftX,
      };
}
