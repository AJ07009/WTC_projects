class Robot {
  late String name;
  late String status;
  late int shields;
  late String position;
  late String direction;

  String getName() {
    return name;
  }

  void setName(String newName) {
    name = newName;
  }

  String getStatus() {
    return status;
  }

  void setStatus(String newStatus) {
    status = newStatus;
  }

  String getPosition() {
    return position;
  }

  String getRobotX() {
    final splitPos = position.split(',');
    List splitList = [];
    for (int i = 0; i < splitPos.length; i++) {
      splitList.add(splitPos[i]);
    }
    return splitList[0];
  }

  String getRobotY() {
    final splitPos = position.split(',');
    List splitList = [];
    for (int i = 0; i < splitPos.length; i++) {
      splitList.add(splitPos[i]);
    }
    return splitList[1];
  }

  void setPosition(String newPosition) {
    position = newPosition;
  }

  Robot create(String newName, String newStatus, String newPosition) {
    Robot robot =
        Robot(position: "0,0", name: '', status: '', direction: "NORTH");
    robot.setName(newName);
    robot.setPosition(newPosition);
    robot.setStatus(newStatus);
    return robot;
  }

  Robot({
    required this.name,
    required this.status,
    required this.position,
    required this.direction,
  });

  factory Robot.fromJson(Map<String, dynamic> json) {
    return Robot(
        position: json['position'],
        status: json['status'],
        name: json['name'],
        direction: json["direction"]);
  }

  void setDirection(String newDir) {
    direction = newDir;
  }
}
