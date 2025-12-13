import 'package:equatable/equatable.dart';

abstract class TimetableEvent extends Equatable {
  const TimetableEvent();

  @override
  List<Object> get props => [];
}

class FetchTimetable extends TimetableEvent {
  const FetchTimetable();
}
