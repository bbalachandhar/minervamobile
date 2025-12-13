import 'package:flutter/material.dart';
import 'package:minerva_flutter/app/app.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:developer';

Future<void> main() async {
  log('main: Starting app initialization');
  WidgetsFlutterBinding.ensureInitialized();
  log('main: WidgetsFlutterBinding ensured initialized');
  final sharedPreferences = await SharedPreferences.getInstance();
  log('main: SharedPreferences initialized');
  runApp(App(sharedPreferences: sharedPreferences));
  log('main: runApp called');
}
