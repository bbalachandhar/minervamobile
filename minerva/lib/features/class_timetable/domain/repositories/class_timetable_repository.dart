import 'package:equatable/equatable.dart';

class ClassTimetableEntry extends Equatable {
  final String subject;
  final String time;
  final String room;
  final String teacher;

  const ClassTimetableEntry({
    required this.subject,
    required this.time,
    required this.room,
    required this.teacher,
  });

  @override
  List<Object> get props => [subject, time, room, teacher];
}

abstract class ClassTimetableRepository {
  Future<Map<String, List<ClassTimetableEntry>>> getClassTimetable();
}