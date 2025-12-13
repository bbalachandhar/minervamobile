import 'package:flutter/material.dart';
import 'package:minerva_flutter/app/app.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http; // Import http
import 'dart:developer';

Future<void> main() async {
  log('main: Starting app initialization');
  WidgetsFlutterBinding.ensureInitialized();
  log('main: WidgetsFlutterBinding ensured initialized');
  final sharedPreferences = await SharedPreferences.getInstance();
  log('main: SharedPreferences initialized');
  final httpClient = http.Client(); // Create http.Client instance
  runApp(App(sharedPreferences: sharedPreferences, httpClient: httpClient)); // Pass httpClient
  log('main: runApp called');
}
