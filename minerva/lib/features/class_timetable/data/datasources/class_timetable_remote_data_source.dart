import 'dart:convert';
import 'dart:developer';

import 'package:http/http.dart' as http;
import 'package:minerva_flutter/features/class_timetable/data/models/class_timetable_model.dart';
import 'package:minerva_flutter/utils/constants.dart';
import 'package:shared_preferences/shared_preferences.dart';

abstract class ClassTimetableRemoteDataSource {
  Future<Map<String, List<ClassTimetableModel>>> getClassTimetable();
}

class ClassTimetableRemoteDataSourceImpl implements ClassTimetableRemoteDataSource {
  final http.Client client;
  final SharedPreferences sharedPreferences;

  ClassTimetableRemoteDataSourceImpl({required this.client, required this.sharedPreferences});

  @override
  Future<Map<String, List<ClassTimetableModel>>> getClassTimetable() async {
    final String studentId = sharedPreferences.getString(Constants.studentId) ?? '';
    final String apiUrl = sharedPreferences.getString(Constants.apiUrl) ?? '';
    final String userId = sharedPreferences.getString(Constants.userId) ?? '';
    final String accessToken = sharedPreferences.getString(Constants.accessToken) ?? '';

    if (studentId.isEmpty || apiUrl.isEmpty || userId.isEmpty || accessToken.isEmpty) {
      throw Exception('Missing required authentication data or API URL.');
    }

    final String url = apiUrl + Constants.getClassScheduleUrl;

    final Map<String, String> headers = {
      "Client-Service": Constants.clientService,
      "Auth-Key": Constants.authKey,
      "Content-Type": Constants.contentType,
      "User-ID": userId,
      "Authorization": accessToken,
    };

    final Map<String, String> body = {
      "student_id": studentId,
    };

    try {
      final response = await client.post(
        Uri.parse(url),
        headers: headers,
        body: jsonEncode(body),
      );

      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonResponse = jsonDecode(response.body);
        if (jsonResponse['status'] == '200') {
          final Map<String, dynamic> timetableJson = jsonResponse['timetable'];
          final Map<String, List<ClassTimetableModel>> timetable = {};

          timetableJson.forEach((day, dayArray) {
            final List<ClassTimetableModel> dayEntries = [];
            if (dayArray is List) {
              for (var entryJson in dayArray) {
                dayEntries.add(ClassTimetableModel.fromJson(entryJson));
              }
            }
            timetable[day] = dayEntries;
          });
          return timetable;
        } else {
          throw Exception(jsonResponse['errorMsg'] ?? 'Failed to load class timetable');
        }
      } else {
        throw Exception('Failed to load class timetable. Status code: ${response.statusCode}');
      }
    } catch (e) {
      log('Error fetching class timetable: $e');
      throw Exception('Failed to fetch class timetable: $e');
    }
  }
}