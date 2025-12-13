import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/entities/syllabus_status_entity.dart';
import 'package:minerva_flutter/features/syllabus_status/presentation/bloc/syllabus_status_bloc.dart';
import 'package:minerva_flutter/features/syllabus_status/presentation/bloc/syllabus_status_event.dart';
import 'package:minerva_flutter/features/syllabus_status/presentation/bloc/syllabus_status_state.dart';

class SyllabusStatusPage extends StatelessWidget {
  const SyllabusStatusPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Syllabus Status'),
      ),
      body: BlocProvider(
        create: (context) => context.read<SyllabusStatusBloc>()..add(const FetchSyllabusStatus()),
        child: BlocBuilder<SyllabusStatusBloc, SyllabusStatusState>(
          builder: (context, state) {
            if (state is SyllabusStatusLoading) {
              return const Center(child: CircularProgressIndicator());
            } else if (state is SyllabusStatusLoaded) {
              return SyllabusStatusWidget(syllabusStatus: state.syllabusStatus);
            } else if (state is SyllabusStatusError) {
              return Center(child: Text(state.message));
            }
            return const Center(child: Text('No syllabus status found.'));
          },
        ),
      ),
    );
  }
}

class SyllabusStatusWidget extends StatelessWidget {
  final List<SyllabusStatus> syllabusStatus;

  const SyllabusStatusWidget({Key? key, required this.syllabusStatus}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: syllabusStatus.length,
      itemBuilder: (context, index) {
        final statusEntry = syllabusStatus[index];
        return Card(
          margin: const EdgeInsets.all(8.0),
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  statusEntry.subjectName,
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const SizedBox(height: 8),
                Text('Topic: ${statusEntry.topic}'),
                Text('Progress: ${statusEntry.progress}'),
                Text('Status: ${statusEntry.status}'),
                Text('Last Updated: ${statusEntry.lastUpdated}'),
              ],
            ),
          ),
        );
      },
    );
  }
}
