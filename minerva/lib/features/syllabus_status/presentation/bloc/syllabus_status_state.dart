part of 'syllabus_status_bloc.dart';

abstract class SyllabusStatusState extends Equatable {
  const SyllabusStatusState();

  @override
  List<Object> get props => [];
}

class SyllabusStatusInitial extends SyllabusStatusState {}

class SyllabusStatusLoading extends SyllabusStatusState {}

class SyllabusStatusLoaded extends SyllabusStatusState {
  final List<SyllabusStatusEntry> syllabusStatusEntries;

  const SyllabusStatusLoaded(this.syllabusStatusEntries);

  @override
  List<Object> get props => [syllabusStatusEntries];
}

class SyllabusStatusError extends SyllabusStatusState {
  final String message;

  const SyllabusStatusError(this.message);

  @override
  List<Object> get props => [message];
}