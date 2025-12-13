import 'package:minerva_flutter/features/syllabus_status/domain/entities/syllabus_status_entity.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/repositories/syllabus_status_repository.dart';

class GetSyllabusStatusUseCase {
  final SyllabusStatusRepository repository;

  GetSyllabusStatusUseCase({required this.repository});

  Future<List<SyllabusStatus>> call() async {
    return await repository.getSyllabusStatus();
  }
}
