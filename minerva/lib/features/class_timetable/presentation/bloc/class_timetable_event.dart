part of 'class_timetable_bloc.dart';

abstract class ClassTimetableEvent extends Equatable {
  const ClassTimetableEvent();

  @override
  List<Object> get props => [];
}

class FetchClassTimetable extends ClassTimetableEvent {}