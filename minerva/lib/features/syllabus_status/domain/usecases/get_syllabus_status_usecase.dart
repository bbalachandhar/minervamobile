import 'package:equatable/equatable.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/repositories/syllabus_status_repository.dart';

class GetSyllabusStatusUseCase {
  final SyllabusStatusRepository repository;

  GetSyllabusStatusUseCase(this.repository);

  Future<List<SyllabusStatusEntry>> call() async {
    return await repository.getSyllabusStatus();
  }
}

class NoParams extends Equatable {
  @override
  List<Object?> get props => [];
}