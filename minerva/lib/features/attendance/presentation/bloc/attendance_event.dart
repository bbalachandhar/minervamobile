part of 'attendance_bloc.dart';

abstract class AttendanceEvent extends Equatable {
  const AttendanceEvent();

  @override
  List<Object> get props => [];
}

class FetchAttendanceData extends AttendanceEvent {
  final DateTime date;

  const FetchAttendanceData(this.date);

  @override
  List<Object> get props => [date];
}
