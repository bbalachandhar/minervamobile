import 'package:equatable/equatable.dart';

class SyllabusStatusEntry extends Equatable {
  final String subject;
  final String topic;
  final String status;

  const SyllabusStatusEntry({
    required this.subject,
    required this.topic,
    required this.status,
  });

  @override
  List<Object> get props => [subject, topic, status];
}

abstract class SyllabusStatusRepository {
  Future<List<SyllabusStatusEntry>> getSyllabusStatus();
}