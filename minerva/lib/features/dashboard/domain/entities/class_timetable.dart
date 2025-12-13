import 'package:equatable/equatable.dart';

class ClassTimetable extends Equatable {
  final String subjectName;
  final String teacherName;
  final String startTime;
  final String endTime;
  final String roomNo;
  final String dayOfWeek;

  const ClassTimetable({
    required this.subjectName,
    required this.teacherName,
    required this.startTime,
    required this.endTime,
    required this.roomNo,
    required this.dayOfWeek,
  });

  factory ClassTimetable.fromJson(Map<String, dynamic> json) {
    final name = json['name'] as String? ?? '';
    final surname = json['surname'] as String? ?? '';
    final teacherFullName = (name.isNotEmpty && surname.isNotEmpty) ? '$name $surname' : (name.isNotEmpty ? name : surname);

    return ClassTimetable(
      subjectName: json['subject_name'] as String? ?? 'N/A',
      teacherName: teacherFullName.isNotEmpty ? teacherFullName : 'N/A',
      startTime: json['time_from'] as String? ?? 'N/A',
      endTime: json['time_to'] as String? ?? 'N/A',
      roomNo: json['room_no']?.toString() ?? 'N/A', // Handle null directly on json['room_no']
      dayOfWeek: json['day'] as String? ?? 'N/A',
    );
  }

  @override
  List<Object?> get props => [
        subjectName,
        teacherName,
        startTime,
        endTime,
        roomNo,
        dayOfWeek,
      ];
}