import 'package:minerva_flutter/features/timetable/domain/entities/timetable_entity.dart';
import 'package:minerva_flutter/features/timetable/domain/repositories/timetable_repository.dart';
import 'package:minerva_flutter/features/timetable/data/datasources/timetable_remote_data_source.dart';

class TimetableRepositoryImpl implements TimetableRepository {
  final TimetableRemoteDataSource remoteDataSource;

  TimetableRepositoryImpl({required this.remoteDataSource});

  @override
  Future<List<Timetable>> getTimetable() async {
    return await remoteDataSource.getTimetable();
  }
}
