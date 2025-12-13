import 'package:equatable/equatable.dart';

class Attendance extends Equatable {
  final String subject;
  final String time;
  final String status;
  final String remark;

  const Attendance({
    required this.subject,
    required this.time,
    required this.status,
    required this.remark,
  });

  @override
  List<Object> get props => [subject, time, status, remark];
}
