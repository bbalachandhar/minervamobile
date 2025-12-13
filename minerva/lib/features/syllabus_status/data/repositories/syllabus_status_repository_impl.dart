import 'package:minerva_flutter/features/syllabus_status/data/datasources/syllabus_status_remote_data_source.dart';
import 'package:minerva_flutter/features/syllabus_status/data/models/syllabus_status_model.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/repositories/syllabus_status_repository.dart';

class SyllabusStatusRepositoryImpl implements SyllabusStatusRepository {
  final SyllabusStatusRemoteDataSource remoteDataSource;

  SyllabusStatusRepositoryImpl({required this.remoteDataSource});

  @override
  Future<List<SyllabusStatusEntry>> getSyllabusStatus() async {
    return await remoteDataSource.getSyllabusStatus();
  }
}