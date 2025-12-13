import 'package:minerva_flutter/features/dashboard/domain/entities/class_timetable.dart';

abstract class ClassTimetableRepository {
  Future<List<ClassTimetable>> getClassTimetable();
}