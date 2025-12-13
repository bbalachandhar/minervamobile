import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:minerva_flutter/features/class_timetable/domain/repositories/class_timetable_repository.dart';
import 'package:minerva_flutter/features/class_timetable/domain/usecases/get_class_timetable.dart';

part 'class_timetable_event.dart';
part 'class_timetable_state.dart';

class ClassTimetableBloc extends Bloc<ClassTimetableEvent, ClassTimetableState> {
  final GetClassTimetable getClassTimetable;

  ClassTimetableBloc({required this.getClassTimetable}) : super(ClassTimetableInitial()) {
    on<FetchClassTimetable>(_onFetchClassTimetable);
  }

  void _onFetchClassTimetable(
      FetchClassTimetable event, Emitter<ClassTimetableState> emit) async {
    emit(ClassTimetableLoading());
    try {
      final timetable = await getClassTimetable();
      emit(ClassTimetableLoaded(timetable));
    } catch (e) {
      emit(ClassTimetableError(e.toString()));
    }
  }
}
