import 'dart:convert';
import 'dart:developer';

import 'package:http/http.dart' as http;
import 'package:minerva_flutter/features/syllabus_status/data/models/syllabus_status_model.dart';
import 'package:minerva_flutter/utils/constants.dart';
import 'package:shared_preferences/shared_preferences.dart';

abstract class SyllabusStatusRemoteDataSource {
  Future<List<SyllabusStatusModel>> getSyllabusStatus();
}

class SyllabusStatusRemoteDataSourceImpl implements SyllabusStatusRemoteDataSource {
  final http.Client client;
  final SharedPreferences sharedPreferences;

  SyllabusStatusRemoteDataSourceImpl({required this.client, required this.sharedPreferences});

  @override
  Future<List<SyllabusStatusModel>> getSyllabusStatus() async {
    final String studentId = sharedPreferences.getString(Constants.studentId) ?? '';
    final String apiUrl = sharedPreferences.getString(Constants.apiUrl) ?? '';
    final String userId = sharedPreferences.getString(Constants.userId) ?? '';
    final String accessToken = sharedPreferences.getString(Constants.accessToken) ?? '';

    log('SyllabusStatus - DEBUG - studentId: "$studentId"');
    log('SyllabusStatus - DEBUG - apiUrl (raw): "$apiUrl"');
    log('SyllabusStatus - DEBUG - userId: "$userId"');
    log('SyllabusStatus - DEBUG - accessToken: "$accessToken"');

    if (apiUrl.isEmpty) { // Explicitly check apiUrl first
      throw Exception('Missing API URL from sharedPreferences.');
    }

    if (studentId.isEmpty || userId.isEmpty || accessToken.isEmpty) {
      String missingParams = '';
      if (studentId.isEmpty) missingParams += 'studentId, ';
      if (userId.isEmpty) missingParams += 'userId, ';
      if (accessToken.isEmpty) missingParams += 'accessToken, ';
      throw Exception('Missing required authentication data: ${missingParams.trim()}');
    }

    final String urlString = apiUrl + Constants.getsyllabussubjectsUrl;

    Uri url;
    try {
      url = Uri.parse(urlString);
    } on FormatException catch (e) {
      log('SyllabusStatus - URL Format Exception: $e. Malformed URL: $urlString');
      throw Exception('Malformed URL: $urlString');
    }

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

    log('SyllabusStatus API Request URL: $urlString');
    log('SyllabusStatus API Request Headers: $headers');
    log('SyllabusStatus API Request Body: $body');

    try {
      final response = await client.post(
        url,
        headers: headers,
        body: jsonEncode(body),
      );

      log('SyllabusStatus API Response Status Code: ${response.statusCode}');
      log('SyllabusStatus API Response Body: ${response.body}');

      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonResponse = jsonDecode(response.body);
        final List<dynamic> subjectsJson = jsonResponse['subjects'];
        return subjectsJson.map((json) => SyllabusStatusModel.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load syllabus status. HTTP Status code: ${response.statusCode}');
      }
    } catch (e) {
      log('Error fetching syllabus status: $e');
      throw Exception('Failed to fetch syllabus status: $e');
    }
  }
}
