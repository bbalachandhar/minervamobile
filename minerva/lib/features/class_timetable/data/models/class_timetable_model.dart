import 'package:minerva_flutter/features/class_timetable/domain/repositories/class_timetable_repository.dart';

class ClassTimetableModel extends ClassTimetableEntry {
  const ClassTimetableModel({
    required String subject,
    required String time,
    required String room,
    required String teacher,
  }) : super(
          subject: subject,
          time: time,
          room: room,
          teacher: teacher,
        );

  factory ClassTimetableModel.fromJson(Map<String, dynamic> json) {
    String timeSlot;
    if (json['time_from'] != null && json['time_from'].isNotEmpty) {
      timeSlot = "${json['time_from']} - ${json['time_to']}";
    } else {
      timeSlot = 'Not Scheduled'; // Equivalent to R.string.notScheduled
    }

    String subjectName = json['subject_name'];
    String subjectCode = json['code'];
    String fullSubject = subjectCode.isNotEmpty ? '$subjectName ($subjectCode)' : subjectName;

    return ClassTimetableModel(
      subject: fullSubject,
      time: timeSlot,
      room: json['room_no'],
      teacher: '', // Teacher information is not available in the current JSON structure.
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'subject': subject,
      'time': time,
      'room': room,
      'teacher': teacher,
    };
  }
}
