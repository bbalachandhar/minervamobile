import 'dart:convert';
import 'dart:developer';

import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/entities/syllabus_status_entity.dart';
import 'package:minerva_flutter/utils/constants.dart';

abstract class SyllabusStatusRemoteDataSource {
  Future<List<SyllabusStatus>> getSyllabusStatus();
}

class SyllabusStatusRemoteDataSourceImpl implements SyllabusStatusRemoteDataSource {
  final SharedPreferences sharedPreferences;

  SyllabusStatusRemoteDataSourceImpl({required this.sharedPreferences});

  @override
  Future<List<SyllabusStatus>> getSyllabusStatus() async {
    final apiUrl = sharedPreferences.getString(Constants.apiUrl);
    final userId = sharedPreferences.getString(Constants.userId);
    final accessToken = sharedPreferences.getString(Constants.accessToken);
    final loginType = sharedPreferences.getString(Constants.loginType);
    final classId = '1'; // Temporarily hardcoded for debugging
    final sectionId = '1'; // Temporarily hardcoded for debugging

    if (apiUrl == null || userId == null || accessToken == null || loginType == null) {
      log('SyllabusStatusRemoteDataSource: Missing required authentication or API information.');
      throw Exception('Missing required authentication or API information.');
    }

    final url = Uri.parse('$apiUrl${Constants.getSyllabusStatusUrl}');
    log('SyllabusStatusRemoteDataSource: API URL: $url');

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

      log('SyllabusStatusRemoteDataSource: Response Status Code: ${response.statusCode}');
      log('SyllabusStatusRemoteDataSource: Response Body: ${response.body}');

      if (response.statusCode == 200) {
        final Map<String, dynamic> responseData = json.decode(response.body);
        if (responseData['status'] == 1 && responseData['syllabus_status'] != null) {
          final List<dynamic> syllabusStatusJson = responseData['syllabus_status'];
          return syllabusStatusJson.map((json) => SyllabusStatus.fromJson(json)).toList();
        } else {
          log('SyllabusStatusRemoteDataSource: API response indicates failure or empty syllabus status: ${responseData['msg']}');
          return [];
        }
      } else {
        throw Exception('Failed to load syllabus status. Status code: ${response.statusCode}');
      }
    } catch (e) {
      log('SyllabusStatusRemoteDataSource: Error fetching syllabus status: $e');
      rethrow;
    }
  }
}
