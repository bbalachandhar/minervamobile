import 'package:equatable/equatable.dart';
import 'package:minerva_flutter/features/class_timetable/domain/repositories/class_timetable_repository.dart';

class GetClassTimetable {
  final ClassTimetableRepository repository;

  GetClassTimetable(this.repository);

  Future<Map<String, List<ClassTimetableEntry>>> call() async {
    return await repository.getClassTimetable();
  }
}

class NoParams extends Equatable {
  @override
  List<Object?> get props => [];
}