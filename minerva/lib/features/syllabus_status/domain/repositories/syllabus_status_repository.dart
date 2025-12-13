import 'package:minerva_flutter/features/syllabus_status/domain/entities/syllabus_status_entity.dart';

abstract class SyllabusStatusRepository {
  Future<List<SyllabusStatus>> getSyllabusStatus();
}
