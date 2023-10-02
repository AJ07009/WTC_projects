class Maze {
  late int topX;
  late int topY;
  late int bottomX;
  late int bottomY;

  late List<dynamic> obstacles = [];

  void setTopX(int x) {
    topX = x;
  }

  void setTopY(int y) {
    topY = y;
  }

  void setBottomX(int x) {
    bottomX = x;
  }

  void setBottomY(int y) {
    bottomY = y;
  }

  String getTopLeft() {
    return "" + topX.toString() + ", " + topY.toString();
  }

  String getBottomRight() {
    return "" + bottomX.toString() + ", " + bottomY.toString();
  }

  void setObs(List<dynamic> newObstacles) {
    obstacles = newObstacles;
  }

  Maze create(int upperX, int upperY, int lowerX, int lowerY,
      List<String> obsList, List<String> pitList) {
    Maze maze = Maze(obstacles: []);

    maze.setTopX(upperX);
    maze.setTopY(upperY);
    maze.setBottomX(lowerX);
    maze.setBottomY(lowerY);
    maze.setObs(obsList);

    return maze;
  }

  Maze({
    required this.obstacles,
  });

  factory Maze.fromJson(Map<String, dynamic> json) {
    if (json["obstaclesList"] != null) {
      return Maze(obstacles: json["obstaclesList"]);
    } else {
      return Maze(obstacles: []);
    }
  }
}
