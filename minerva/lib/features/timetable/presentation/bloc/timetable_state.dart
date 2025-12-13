import 'package:equatable/equatable.dart';
import 'package:minerva_flutter/features/timetable/domain/entities/timetable_entity.dart';

abstract class TimetableState extends Equatable {
  const TimetableState();

  @override
  List<Object> get props => [];
}

class TimetableInitial extends TimetableState {}

class TimetableLoading extends TimetableState {}

class TimetableLoaded extends TimetableState {
  final List<Timetable> timetable;

  const TimetableLoaded({required this.timetable});

  @override
  List<Object> get props => [timetable];
}

class TimetableError extends TimetableState {
  final String message;

  const TimetableError({required this.message});

  @override
  List<Object> get props => [message];
}
