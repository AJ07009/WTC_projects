import 'package:client/Models/DatabaseWorld/database_world_view_model.dart';
import 'package:client/Models/osbstacles/obstacle_list_view_model.dart';
import 'package:client/Models/robot/robot_list_view_model.dart';
import 'package:client/Models/world/world_view_model.dart';
import 'package:client/Widgets/splashy.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider<RobotListViewModel>(
            create: (_) => RobotListViewModel()),
        ChangeNotifierProvider<WorldViewModel>(create: (_) => WorldViewModel()),
        ChangeNotifierProvider<DatabaseWorldViewModel>(
            create: (_) => DatabaseWorldViewModel()),
        ChangeNotifierProvider<ObstacleListViewModel>(
            create: (_) => ObstacleListViewModel())
      ],
      child: MaterialApp(
          title: 'Flutter Demo',
          debugShowCheckedModeBanner: false,
          theme: ThemeData.dark(),
          home: SplashScreen()),
    );
  }
}
