part of 'syllabus_status_bloc.dart';

abstract class SyllabusStatusEvent extends Equatable {
  const SyllabusStatusEvent();

  @override
  List<Object> get props => [];
}

class FetchSyllabusStatus extends SyllabusStatusEvent {}