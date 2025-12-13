import 'package:minerva_flutter/features/timetable/domain/entities/timetable_entity.dart';
import 'package:minerva_flutter/features/timetable/domain/repositories/timetable_repository.dart';

class GetTimetableUseCase {
  final TimetableRepository repository;

  GetTimetableUseCase({required this.repository});

  Future<List<Timetable>> call() async {
    return await repository.getTimetable();
  }
}
