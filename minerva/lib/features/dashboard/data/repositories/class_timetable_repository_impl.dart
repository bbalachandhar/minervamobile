import 'package:minerva_flutter/features/dashboard/data/datasources/class_timetable_remote_data_source.dart';
import 'package:minerva_flutter/features/dashboard/domain/entities/class_timetable.dart';
import 'package:minerva_flutter/features/dashboard/domain/repositories/class_timetable_repository.dart';

class ClassTimetableRepositoryImpl implements ClassTimetableRepository {
  final ClassTimetableRemoteDataSource remoteDataSource;

  ClassTimetableRepositoryImpl({required this.remoteDataSource});

  @override
  Future<List<ClassTimetable>> getClassTimetable() async {
    try {
      final timetable = await remoteDataSource.getClassTimetable();
      return timetable;
    } catch (e) {
      // In a real application, this would involve more sophisticated error handling
      // and potentially emitting custom exceptions.
      rethrow;
    }
  }
}