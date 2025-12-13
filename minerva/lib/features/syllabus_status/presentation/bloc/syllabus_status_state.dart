import 'package:equatable/equatable.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/entities/syllabus_status_entity.dart';

abstract class SyllabusStatusState extends Equatable {
  const SyllabusStatusState();

  @override
  List<Object> get props => [];
}

class SyllabusStatusInitial extends SyllabusStatusState {}

class SyllabusStatusLoading extends SyllabusStatusState {}

class SyllabusStatusLoaded extends SyllabusStatusState {
  final List<SyllabusStatus> syllabusStatus;

  const SyllabusStatusLoaded({required this.syllabusStatus});

  @override
  List<Object> get props => [syllabusStatus];
}

class SyllabusStatusError extends SyllabusStatusState {
  final String message;

  const SyllabusStatusError({required this.message});

  @override
  List<Object> get props => [message];
}
