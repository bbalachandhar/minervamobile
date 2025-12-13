import 'package:minerva_flutter/features/dashboard/domain/entities/class_timetable.dart';
import 'package:minerva_flutter/features/dashboard/domain/repositories/class_timetable_repository.dart';

class GetClassTimetable {
  final ClassTimetableRepository repository;

  GetClassTimetable(this.repository);

  Future<List<ClassTimetable>> call() async {
    return await repository.getClassTimetable();
  }
}