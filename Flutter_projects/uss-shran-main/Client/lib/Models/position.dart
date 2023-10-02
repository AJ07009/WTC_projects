// class Position {
//   late int x;
//   late int y;
//
//   void setX(int newX) {
//     x = newX;
//   }
//
//   void setY(int newY) {
//     y = newY;
//   }
//
//   int getX() {
//     return x;
//   }
//
//   int getY() {
//     return y;
//   }
//
//   Position setPosition(Position newPosition) {
//     Position position = Position(x: 0, y: 0);
//     position.setX(newPosition.getX());
//     position.setY(newPosition.getY());
//     return position;
//   }
//
//   Position getPosition() {
//     return Position(x: x, y: y);
//   }
//
//   Position create(int x, int y) {
//     Position position = Position(x: 0, y: 0);
//
//     position.setX(x);
//     position.setY(y);
//
//     return position;
//   }
//
//   Position({required this.x, required this.y});
// }
