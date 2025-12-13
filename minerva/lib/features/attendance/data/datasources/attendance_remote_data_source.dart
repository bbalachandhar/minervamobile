import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:minerva_flutter/features/attendance/data/models/attendance_model.dart';
import 'package:minerva_flutter/utils/constants.dart';
import 'package:shared_preferences/shared_preferences.dart';

abstract class AttendanceRemoteDataSource {
  Future<List<AttendanceModel>> getAttendance(DateTime date);
}

class AttendanceRemoteDataSourceImpl implements AttendanceRemoteDataSource {
  final http.Client client;
  final SharedPreferences sharedPreferences;

  AttendanceRemoteDataSourceImpl(
      {required this.client, required this.sharedPreferences});

  @override
  Future<List<AttendanceModel>> getAttendance(DateTime date) async {
    final apiUrl = sharedPreferences.getString(Constants.apiUrl);
    final userId = sharedPreferences.getString(Constants.userId);
    final studentId = sharedPreferences.getString(Constants.studentId);
    final accessToken = sharedPreferences.getString(Constants.accessToken);

    if (apiUrl == null || userId == null || accessToken == null || studentId == null) {
      throw Exception('Missing required authentication or API information.');
    }

    final formattedDate = "${date.year}-${date.month}-${date.day}";

    final response = await client.post(
      Uri.parse(apiUrl + "webservice/getAttendenceRecords"),
      headers: {
        'Client-Service': "smartschool",
        'Auth-Key': "schoolAdmin@",
        'Content-Type': 'application/json',
        'User-ID': userId,
        'Authorization': accessToken,
      },
      body: json.encode({
        "student_id": studentId,
        "date": formattedDate,
      }),
    );

    if (response.statusCode == 200) {
      final result = json.decode(response.body);
      if (result['attendence_type'] == "1") {
        final List<dynamic> data = result['data'];
        return data.map((e) => AttendanceModel.fromJson(e)).toList();
      } else {
        // Handle daily attendance if needed
        return [];
      }
    } else {
      throw Exception('Failed to load attendance');
    }
  }
}
