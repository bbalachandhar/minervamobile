import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:minerva_flutter/features/class_timetable/domain/repositories/class_timetable_repository.dart';
import 'package:minerva_flutter/features/class_timetable/presentation/bloc/class_timetable_bloc.dart';
import 'dart:developer';

class ClassTimetablePage extends StatefulWidget {
  const ClassTimetablePage({Key? key}) : super(key: key);

  @override
  State<ClassTimetablePage> createState() => _ClassTimetablePageState();
}

class _ClassTimetablePageState extends State<ClassTimetablePage> with SingleTickerProviderStateMixin {
  late TabController _tabController;
  final List<String> _daysOfWeek = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: _daysOfWeek.length, vsync: this);
    context.read<ClassTimetableBloc>().add(FetchClassTimetable());
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Class Timetable'),
        bottom: TabBar(
          controller: _tabController,
          isScrollable: true,
          tabs: _daysOfWeek.map((day) => Tab(text: day)).toList(),
        ),
      ),
      body: BlocBuilder<ClassTimetableBloc, ClassTimetableState>(
        builder: (context, state) {
          if (state is ClassTimetableLoading) {
            log('ClassTimetablePage - State: ClassTimetableLoading');
            return const Center(child: CircularProgressIndicator());
          } else if (state is ClassTimetableLoaded) {
            log('ClassTimetablePage - State: ClassTimetableLoaded');
            return TabBarView(
              controller: _tabController,
              children: _daysOfWeek.map((day) {
                final dayEntries = state.timetable[day] ?? [];
                if (dayEntries.isEmpty) {
                  return Center(child: Text('No classes scheduled for $day'));
                }
                return ListView.builder(
                  itemCount: dayEntries.length,
                  itemBuilder: (context, index) {
                    final entry = dayEntries[index];
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
                              'Time: ${entry.time}',
                              style: const TextStyle(fontSize: 16),
                            ),
                            if (entry.room.isNotEmpty)
                              Text(
                                'Room: ${entry.room}',
                                style: const TextStyle(fontSize: 16),
                              ),
                            if (entry.teacher.isNotEmpty)
                              Text(
                                'Teacher: ${entry.teacher}',
                                style: const TextStyle(fontSize: 16),
                              ),
                          ],
                        ),
                      ),
                    );
                  },
                );
              }).toList(),
            );
          } else if (state is ClassTimetableError) {
            log('ClassTimetablePage - State: ClassTimetableError - ${state.message}');
            return Center(child: Text('Error: ${state.message}'));
          }
          log('ClassTimetablePage - State: Initial or unknown');
          return const Center(child: Text('Press to load timetable'));
        },
      ),
    );
  }
}