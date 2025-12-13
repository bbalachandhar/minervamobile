import 'dart:convert';
import 'dart:developer';

import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:minerva_flutter/features/timetable/domain/entities/timetable_entity.dart';
import 'package:minerva_flutter/utils/constants.dart';

abstract class TimetableRemoteDataSource {
  Future<List<Timetable>> getTimetable();
}

class TimetableRemoteDataSourceImpl implements TimetableRemoteDataSource {
  final SharedPreferences sharedPreferences;

  TimetableRemoteDataSourceImpl({required this.sharedPreferences});

  @override
  Future<List<Timetable>> getTimetable() async {
    final apiUrl = sharedPreferences.getString(Constants.apiUrl);
    final userId = sharedPreferences.getString(Constants.userId);
    final accessToken = sharedPreferences.getString(Constants.accessToken);
    final loginType = sharedPreferences.getString(Constants.loginType);
    final classId = sharedPreferences.getString(Constants.classId);
    final sectionId = sharedPreferences.getString(Constants.sectionId);


    if (apiUrl == null || userId == null || accessToken == null || loginType == null || classId == null || sectionId == null) {
      log('TimetableRemoteDataSource: Missing required authentication or API information, or class/section ID.');
      throw Exception('Missing required authentication or API information, or class/section ID.');
    }

    // Assuming a new constant for the timetable URL will be added in utils/constants.dart
    final url = Uri.parse('$apiUrl${Constants.getClassScheduleUrl}'); // Using existing one for now
    log('TimetableRemoteDataSource: API URL: $url');

    try {
      final response = await http.post(
        url,
        headers: {
          'Client-Service': Constants.clientService,
          'Auth-Key': Constants.authKey,
          'Content-Type': Constants.contentType,
          'User-ID': userId,
          'Authorization': accessToken,
        },
        body: json.encode({
          'user': loginType,
          'class_id': classId,
          'section_id': sectionId,
        }),
      );

      log('TimetableRemoteDataSource: Response Status Code: ${response.statusCode}');
      log('TimetableRemoteDataSource: Response Body: ${response.body}');

      if (response.statusCode == 200) {
        final Map<String, dynamic> responseData = json.decode(response.body);
        if (responseData['status'] == 1 && responseData['timetable'] != null) {
          final List<dynamic> timetableJson = responseData['timetable'];
          return timetableJson.map((json) => Timetable.fromJson(json)).toList();
        } else {
          log('TimetableRemoteDataSource: API response indicates failure or empty timetable: ${responseData['msg']}');
          return [];
        }
      } else {
        throw Exception('Failed to load timetable. Status code: ${response.statusCode}');
      }
    } catch (e) {
      log('TimetableRemoteDataSource: Error fetching timetable: $e');
      rethrow;
    }
  }
}