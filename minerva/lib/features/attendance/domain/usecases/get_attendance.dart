import 'package:equatable/equatable.dart';
import 'package:minerva_flutter/features/attendance/domain/entities/attendance.dart';
import 'package:minerva_flutter/features/attendance/domain/repositories/attendance_repository.dart';

class GetAttendance {
  final AttendanceRepository repository;

  GetAttendance(this.repository);

  Future<List<Attendance>> call(Params params) async {
    return await repository.getAttendance(params.date);
  }
}

class Params extends Equatable {
  final DateTime date;

  const Params({required this.date});

  @override
  List<Object> get props => [date];
}
