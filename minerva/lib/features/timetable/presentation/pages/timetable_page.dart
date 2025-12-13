import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:minerva_flutter/features/timetable/domain/entities/timetable_entity.dart';
import 'package:minerva_flutter/features/timetable/presentation/bloc/timetable_bloc.dart';
import 'package:minerva_flutter/features/timetable/presentation/bloc/timetable_event.dart';
import 'package:minerva_flutter/features/timetable/presentation/bloc/timetable_state.dart';

class TimetablePage extends StatelessWidget {
  const TimetablePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Class Timetable'),
      ),
      body: BlocProvider(
        create: (context) => context.read<TimetableBloc>()..add(const FetchTimetable()),
        child: BlocBuilder<TimetableBloc, TimetableState>(
          builder: (context, state) {
            if (state is TimetableLoading) {
              return const Center(child: CircularProgressIndicator());
            } else if (state is TimetableLoaded) {
              return TimetableWidget(timetable: state.timetable);
            } else if (state is TimetableError) {
              return Center(child: Text(state.message));
            }
            return const Center(child: Text('No timetable found.'));
          },
        ),
      ),
    );
  }
}

class TimetableWidget extends StatelessWidget {
  final List<Timetable> timetable;

  const TimetableWidget({Key? key, required this.timetable}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: timetable.length,
      itemBuilder: (context, index) {
        final dayTimetable = timetable[index];
        return Card(
          margin: const EdgeInsets.all(8.0),
          child: ExpansionTile(
            title: Text(dayTimetable.day),
            children: dayTimetable.entries.map((entry) {
              return ListTile(
                title: Text(entry.subject),
                subtitle: Text('${entry.teacher} - Room: ${entry.room}'),
                trailing: Text('${entry.startTime} - ${entry.endTime}'),
              );
            }).toList(),
          ),
        );
      },
    );
  }
}
