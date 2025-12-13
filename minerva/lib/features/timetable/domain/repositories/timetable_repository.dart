import 'package:minerva_flutter/features/timetable/domain/entities/timetable_entity.dart';

abstract class TimetableRepository {
  Future<List<Timetable>> getTimetable();
}
