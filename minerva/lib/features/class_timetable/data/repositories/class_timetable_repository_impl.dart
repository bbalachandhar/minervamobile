import 'package:minerva_flutter/features/class_timetable/data/datasources/class_timetable_remote_data_source.dart';
import 'package:minerva_flutter/features/class_timetable/data/models/class_timetable_model.dart';
import 'package:minerva_flutter/features/class_timetable/domain/repositories/class_timetable_repository.dart';

class ClassTimetableRepositoryImpl implements ClassTimetableRepository {
  final ClassTimetableRemoteDataSource remoteDataSource;

  ClassTimetableRepositoryImpl({required this.remoteDataSource});

  @override
  Future<Map<String, List<ClassTimetableModel>>> getClassTimetable() async {
    return await remoteDataSource.getClassTimetable();
  }
}