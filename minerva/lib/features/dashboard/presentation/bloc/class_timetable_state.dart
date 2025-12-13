part of 'class_timetable_bloc.dart';

abstract class ClassTimetableState extends Equatable {
  const ClassTimetableState();

  @override
  List<Object> get props => [];
}

class ClassTimetableInitial extends ClassTimetableState {}

class ClassTimetableLoading extends ClassTimetableState {}

class ClassTimetableLoaded extends ClassTimetableState {
  final List<ClassTimetable> timetable;

  const ClassTimetableLoaded({required this.timetable});

  @override
  List<Object> get props => [timetable];
}

class ClassTimetableError extends ClassTimetableState {
  final String message;

  const ClassTimetableError({required this.message});

  @override
  List<Object> get props => [message];
}