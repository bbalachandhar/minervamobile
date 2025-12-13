import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/usecases/get_syllabus_status_usecase.dart';
import 'syllabus_status_event.dart';
import 'syllabus_status_state.dart';

class SyllabusStatusBloc extends Bloc<SyllabusStatusEvent, SyllabusStatusState> {
  final GetSyllabusStatusUseCase getSyllabusStatusUseCase;

  SyllabusStatusBloc({required this.getSyllabusStatusUseCase}) : super(SyllabusStatusInitial()) {
    on<FetchSyllabusStatus>(_onFetchSyllabusStatus);
  }

  Future<void> _onFetchSyllabusStatus(
    FetchSyllabusStatus event,
    Emitter<SyllabusStatusState> emit,
  ) async {
    emit(SyllabusStatusLoading());
    try {
      final syllabusStatus = await getSyllabusStatusUseCase();
      emit(SyllabusStatusLoaded(syllabusStatus: syllabusStatus));
    } catch (e) {
      emit(SyllabusStatusError(message: e.toString()));
    }
  }
}
