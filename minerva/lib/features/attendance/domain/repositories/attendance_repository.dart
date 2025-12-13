import 'package:minerva_flutter/features/attendance/domain/entities/attendance.dart';

abstract class AttendanceRepository {
  Future<List<Attendance>> getAttendance(DateTime date);
}
