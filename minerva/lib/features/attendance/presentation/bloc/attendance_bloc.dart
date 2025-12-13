import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:minerva_flutter/features/attendance/domain/entities/attendance.dart';
import 'package:minerva_flutter/features/attendance/domain/usecases/get_attendance.dart';

part 'attendance_event.dart';
part 'attendance_state.dart';

class AttendanceBloc extends Bloc<AttendanceEvent, AttendanceState> {
  final GetAttendance getAttendance;

  AttendanceBloc({required this.getAttendance}) : super(AttendanceInitial()) {
    on<FetchAttendanceData>(_onFetchAttendanceData);
  }

  void _onFetchAttendanceData(
      FetchAttendanceData event, Emitter<AttendanceState> emit) async {
    emit(AttendanceLoading());
    try {
      final attendance = await getAttendance(Params(date: event.date));
      emit(AttendanceLoaded(attendance));
    } catch (e) {
      emit(AttendanceError(e.toString()));
    }
  }
}
