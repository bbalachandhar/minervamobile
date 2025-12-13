import 'dart:convert';
import 'dart:developer';

import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:minerva_flutter/features/dashboard/domain/entities/class_timetable.dart';
import 'package:minerva_flutter/utils/constants.dart';

abstract class ClassTimetableRemoteDataSource {
  Future<List<ClassTimetable>> getClassTimetable();
}

class ClassTimetableRemoteDataSourceImpl implements ClassTimetableRemoteDataSource {
  final SharedPreferences sharedPreferences;

  ClassTimetableRemoteDataSourceImpl({required this.sharedPreferences});

  @override
  Future<List<ClassTimetable>> getClassTimetable() async {
    final apiUrl = sharedPreferences.getString(Constants.apiUrl);
    final userId = sharedPreferences.getString(Constants.userId);
    final accessToken = sharedPreferences.getString(Constants.accessToken);
    final loginType = sharedPreferences.getString(Constants.loginType);
    final classId = sharedPreferences.getString(Constants.classId);
    final sectionId = sharedPreferences.getString(Constants.sectionId);

    if (apiUrl == null || userId == null || accessToken == null || loginType == null || classId == null || sectionId == null) {
      log('ClassTimetableRemoteDataSource: Missing required authentication or API information, or class/section ID.');
      throw Exception('Missing required authentication or API information, or class/section ID.');
    }

    final url = Uri.parse('$apiUrl${Constants.getClassScheduleUrl}');
    log('ClassTimetableRemoteDataSource: API URL: $url');

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

      log('ClassTimetableRemoteDataSource: Response Status Code: ${response.statusCode}');
      log('ClassTimetableRemoteDataSource: Response Body: ${response.body}');

      if (response.statusCode == 200) {
        final Map<String, dynamic> responseData = json.decode(response.body);
        if (responseData['status'] == 1 && responseData['timetable'] != null) {
          final List<dynamic> timetableJson = responseData['timetable'];
          return timetableJson.map((json) => ClassTimetable.fromJson(json)).toList();
        } else {
          log('ClassTimetableRemoteDataSource: API response indicates failure or empty timetable: ${responseData['msg']}');
          return []; // Or throw an error if an empty timetable is an error case
        }
      } else {
        throw Exception('Failed to load class timetable. Status code: ${response.statusCode}');
      }
    } catch (e) {
      log('ClassTimetableRemoteDataSource: Error fetching class timetable: $e');
      rethrow;
    }
  }
}