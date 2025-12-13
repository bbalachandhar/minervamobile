import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:minerva_flutter/features/timetable/domain/usecases/get_timetable_usecase.dart';
import 'timetable_event.dart';
import 'timetable_state.dart';

class TimetableBloc extends Bloc<TimetableEvent, TimetableState> {
  final GetTimetableUseCase getTimetableUseCase;

  TimetableBloc({required this.getTimetableUseCase}) : super(TimetableInitial()) {
    on<FetchTimetable>(_onFetchTimetable);
  }

  Future<void> _onFetchTimetable(
    FetchTimetable event,
    Emitter<TimetableState> emit,
  ) async {
    emit(TimetableLoading());
    try {
      final timetable = await getTimetableUseCase();
      emit(TimetableLoaded(timetable: timetable));
    } catch (e) {
      emit(TimetableError(message: e.toString()));
    }
  }
}
