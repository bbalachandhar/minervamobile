import 'package:minerva_flutter/features/syllabus_status/domain/repositories/syllabus_status_repository.dart';

class SyllabusStatusModel extends SyllabusStatusEntry {
  final String id;
  final String subjectGroupSubjectId;

  const SyllabusStatusModel({
    required String subject,
    required String topic, // This maps to total_complete/total
    required String status,
    required this.id,
    required this.subjectGroupSubjectId,
  }) : super(
          subject: subject,
          topic: topic,
          status: status,
        );

  factory SyllabusStatusModel.fromJson(Map<String, dynamic> json) {
    String subjectName = json['subject_name'];
    String subjectCode = json['subject_code'];
    String fullSubject = subjectCode.isNotEmpty ? '$subjectName ($subjectCode)' : subjectName;

    String totalComplete = json['total_complete'];
    String total = json['total'];
    String topicStatus = '$totalComplete of $total Topics Completed';

    // The 'status' field in SyllabusStatusEntry will be derived from completion, or a specific status if available in JSON
    // For now, let's use the topicStatus as the overall status for display in the entry.
    String statusText = totalComplete == total ? 'Completed' : 'In Progress';


    return SyllabusStatusModel(
      subject: fullSubject,
      topic: topicStatus,
      status: statusText,
      id: json['id'],
      subjectGroupSubjectId: json['subject_group_subject_id'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'subject_name': subject,
      'total_complete': topic, // This is a simplification; need to extract numbers if needed for API
      'id': id,
      'subject_group_subject_id': subjectGroupSubjectId,
      // Status is derived, not directly stored
    };
  }
}
