import 'package:minerva_flutter/features/syllabus_status/domain/entities/syllabus_status_entity.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/repositories/syllabus_status_repository.dart';
import 'package:minerva_flutter/features/syllabus_status/data/datasources/syllabus_status_remote_data_source.dart';

class SyllabusStatusRepositoryImpl implements SyllabusStatusRepository {
  final SyllabusStatusRemoteDataSource remoteDataSource;

  SyllabusStatusRepositoryImpl({required this.remoteDataSource});

  @override
  Future<List<SyllabusStatus>> getSyllabusStatus() async {
    return await remoteDataSource.getSyllabusStatus();
  }
}
