import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/repositories/syllabus_status_repository.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/usecases/get_syllabus_status_usecase.dart';

part 'syllabus_status_event.dart';
part 'syllabus_status_state.dart';

class SyllabusStatusBloc extends Bloc<SyllabusStatusEvent, SyllabusStatusState> {
  final GetSyllabusStatusUseCase getSyllabusStatusUseCase;

  SyllabusStatusBloc({required this.getSyllabusStatusUseCase}) : super(SyllabusStatusInitial()) {
    on<FetchSyllabusStatus>(_onFetchSyllabusStatus);
  }

  void _onFetchSyllabusStatus(
      FetchSyllabusStatus event, Emitter<SyllabusStatusState> emit) async {
    emit(SyllabusStatusLoading());
    try {
      final syllabusStatus = await getSyllabusStatusUseCase();
      emit(SyllabusStatusLoaded(syllabusStatus));
    } catch (e) {
      emit(SyllabusStatusError(e.toString()));
    }
  }
}