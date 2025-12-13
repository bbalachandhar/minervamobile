import 'package:equatable/equatable.dart';

class SyllabusStatus extends Equatable {
  const SyllabusStatus({
    required this.subjectName,
    required this.topic,
    required this.progress,
    required this.status,
    required this.lastUpdated,
  });

  factory SyllabusStatus.fromJson(Map<String, dynamic> json) {
    return SyllabusStatus(
      subjectName: json['subject_name'] as String,
      topic: json['topic'] as String,
      progress: json['progress'] as String, // Assuming percentage as string e.g., "75%"
      status: json['status'] as String,
      lastUpdated: json['last_updated'] as String,
    );
  }

  final String subjectName;
  final String topic;
  final String progress;
  final String status;
  final String lastUpdated;

  @override
  List<Object?> get props => [subjectName, topic, progress, status, lastUpdated];
}
