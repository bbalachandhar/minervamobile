import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/repositories/syllabus_status_repository.dart';
import 'package:minerva_flutter/features/syllabus_status/presentation/bloc/syllabus_status_bloc.dart';
import 'dart:developer';

class SyllabusStatusPage extends StatefulWidget {
  const SyllabusStatusPage({Key? key}) : super(key: key);

  @override
  State<SyllabusStatusPage> createState() => _SyllabusStatusPageState();
}

class _SyllabusStatusPageState extends State<SyllabusStatusPage> {
  @override
  void initState() {
    super.initState();
    _fetchSyllabusStatus();
  }

  Future<void> _fetchSyllabusStatus() async {
    context.read<SyllabusStatusBloc>().add(FetchSyllabusStatus());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Syllabus Status'),
      ),
      body: BlocBuilder<SyllabusStatusBloc, SyllabusStatusState>(
        builder: (context, state) {
          if (state is SyllabusStatusLoading) {
            log('SyllabusStatusPage - State: SyllabusStatusLoading');
            return const Center(child: CircularProgressIndicator());
          } else if (state is SyllabusStatusLoaded) {
            log('SyllabusStatusPage - State: SyllabusStatusLoaded');
            return RefreshIndicator(
              onRefresh: _fetchSyllabusStatus,
              child: state.syllabusStatusEntries.isEmpty
                  ? const Center(child: Text('No syllabus status available.'))
                  : ListView.builder(
                      itemCount: state.syllabusStatusEntries.length,
                      itemBuilder: (context, index) {
                        final entry = state.syllabusStatusEntries[index];
                        return Card(
                          margin: const EdgeInsets.all(8.0),
                          child: Padding(
                            padding: const EdgeInsets.all(16.0),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  entry.subject,
                                  style: const TextStyle(
                                    fontSize: 18,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                const SizedBox(height: 5),
                                Text(
                                  entry.topic,
                                  style: const TextStyle(fontSize: 16),
                                ),
                                Text(
                                  'Status: ${entry.status}',
                                  style: TextStyle(
                                    fontSize: 16,
                                    color: entry.status == 'Completed' ? Colors.green : Colors.orange,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                // You might want to add a progress indicator here
                                // For example: LinearProgressIndicator(value: (double.tryParse(entry.topic.split(' ')[0]) ?? 0) / (double.tryParse(entry.topic.split(' ')[3]) ?? 1)),
                              ],
                            ),
                          ),
                        );
                      },
                    ),
            );
          } else if (state is SyllabusStatusError) {
            log('SyllabusStatusPage - State: SyllabusStatusError - ${state.message}');
            return RefreshIndicator(
              onRefresh: _fetchSyllabusStatus,
              child: Center(child: Text('Error: ${state.message}')),
            );
          }
          log('SyllabusStatusPage - State: Initial or unknown');
          return RefreshIndicator(
            onRefresh: _fetchSyllabusStatus,
            child: const Center(child: Text('Pull to refresh Syllabus Status')),
          );
        },
      ),
    );
  }
}
