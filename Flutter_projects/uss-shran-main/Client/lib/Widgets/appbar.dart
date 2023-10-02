import 'package:flutter/material.dart';

class AppBarCustom extends StatefulWidget implements PreferredSizeWidget{
  final String title;
  final bool back;
  const AppBarCustom({Key? key, required this.title, required this.back}) : super(key: key);

  @override
  _AppBarCustomState createState() => _AppBarCustomState();

  @override
  Size get preferredSize => const Size.fromHeight(80);
}

class _AppBarCustomState extends State<AppBarCustom> {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      backgroundColor: Colors.blue,
      automaticallyImplyLeading: false,
      title: Text(
        widget.title,
        style: const TextStyle(
          color: Colors.black,
          fontSize: 30,
          fontWeight: FontWeight.bold,
          fontStyle: FontStyle.italic,
        ),
      ),
      centerTitle: widget.back,
      elevation: 4,
    );
  }
}
