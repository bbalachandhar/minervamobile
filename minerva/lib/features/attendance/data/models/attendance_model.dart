import 'package:minerva_flutter/features/attendance/domain/entities/attendance.dart';

class AttendanceModel extends Attendance {
  const AttendanceModel({
    required String subject,
    required String time,
    required String status,
    required String remark,
  }) : super(
          subject: subject,
          time: time,
          status: status,
          remark: remark,
        );

  factory AttendanceModel.fromJson(Map<String, dynamic> json) {
    return AttendanceModel(
      subject: json['name'] + (json['code'] == '' ? '' : ' (' + json['code'] + ')'),
      time: json['time_from'] + '-' + json['time_to'],
      status: json['type'],
      remark: json['remark'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'subject': subject,
      'time': time,
      'status': status,
      'remark': remark,
    };
  }
}
