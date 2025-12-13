part of 'class_timetable_bloc.dart';

abstract class ClassTimetableState extends Equatable {
  const ClassTimetableState();

  @override
  List<Object> get props => [];
}

class ClassTimetableInitial extends ClassTimetableState {}

class ClassTimetableLoading extends ClassTimetableState {}

class ClassTimetableLoaded extends ClassTimetableState {
  final Map<String, List<ClassTimetableEntry>> timetable;

  const ClassTimetableLoaded(this.timetable);

  @override
  List<Object> get props => [timetable];
}

class ClassTimetableError extends ClassTimetableState {
  final String message;

  const ClassTimetableError(this.message);

  @override
  List<Object> get props => [message];
}