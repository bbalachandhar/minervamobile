import 'package:minerva_flutter/features/attendance/data/datasources/attendance_remote_data_source.dart';
import 'package:minerva_flutter/features/attendance/domain/entities/attendance.dart';
import 'package:minerva_flutter/features/attendance/domain/repositories/attendance_repository.dart';

class AttendanceRepositoryImpl implements AttendanceRepository {
  final AttendanceRemoteDataSource remoteDataSource;

  AttendanceRepositoryImpl({required this.remoteDataSource});

  @override
  Future<List<Attendance>> getAttendance(DateTime date) async {
    return await remoteDataSource.getAttendance(date);
  }
}
