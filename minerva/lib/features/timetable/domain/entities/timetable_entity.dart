import 'package:equatable/equatable.dart';

class Timetable extends Equatable {
  const Timetable({
    required this.day,
    required this.entries,
  });

  factory Timetable.fromJson(Map<String, dynamic> json) {
    return Timetable(
      day: json['day'] as String,
      entries: (json['entries'] as List<dynamic>)
          .map((e) => TimetableEntry.fromJson(e as Map<String, dynamic>))
          .toList(),
    );
  }

  final String day;
  final List<TimetableEntry> entries;

  @override
  List<Object?> get props => [day, entries];
}

class TimetableEntry extends Equatable {
  const TimetableEntry({
    required this.startTime,
    required this.endTime,
    required this.subject,
    required this.teacher,
    required this.room,
  });

  factory TimetableEntry.fromJson(Map<String, dynamic> json) {
    return TimetableEntry(
      startTime: json['start_time'] as String,
      endTime: json['end_time'] as String,
      subject: json['subject'] as String,
      teacher: json['teacher'] as String,
      room: json['room'] as String,
    );
  }

  final String startTime;
  final String endTime;
  final String subject;
  final String teacher;
  final String room;

  @override
  List<Object?> get props => [startTime, endTime, subject, teacher, room];
}
