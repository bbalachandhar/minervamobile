import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:minerva_flutter/features/attendance/presentation/bloc/attendance_bloc.dart';
import 'package:table_calendar/table_calendar.dart';

class AttendancePage extends StatefulWidget {
  const AttendancePage({Key? key}) : super(key: key);

  @override
  State<AttendancePage> createState() => _AttendancePageState();
}

class _AttendancePageState extends State<AttendancePage> {
  DateTime _focusedDay = DateTime.now();
  DateTime? _selectedDay;

  @override
  void initState() {
    super.initState();
    _selectedDay = _focusedDay;
    // Fetch initial data
    context.read<AttendanceBloc>().add(FetchAttendanceData(_selectedDay!));
  }

  void _presentDatePicker() {
    showDatePicker(
      context: context,
      initialDate: _focusedDay,
      firstDate: DateTime(2020),
      lastDate: DateTime(2030),
    ).then((pickedDate) {
      if (pickedDate == null) {
        return;
      }
      setState(() {
        _selectedDay = pickedDate;
        _focusedDay = pickedDate;
      });
      context.read<AttendanceBloc>().add(FetchAttendanceData(_selectedDay!));
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Attendance'),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Card(
              margin: const EdgeInsets.all(10),
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20),
              ),
              child: Padding(
                padding: const EdgeInsets.all(10.0),
                child: Row(
                  children: [
                    const Expanded(
                      child: Text(
                        'Attendance',
                        style: TextStyle(
                          fontSize: 22,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    Expanded(
                      child: Image.asset(
                        'assets/attendancepage.jpg',
                        height: 110,
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Card(
              margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(15),
              ),
              child: Padding(
                padding: const EdgeInsets.all(10.0),
                child: Column(
                  children: [
                    Row(
                      children: [
                        const Text(
                          'Date:',
                          style: TextStyle(
                              fontSize: 16, fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(width: 10),
                        InkWell(
                          onTap: _presentDatePicker,
                          child: Text(
                            DateFormat.yMMMMd().format(_selectedDay!),
                            style: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: Theme.of(context).primaryColor),
                          ),
                        ),
                      ],
                    ),
                    const Divider(),
                    const Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Expanded(
                            child: Text('Subject',
                                style: TextStyle(fontWeight: FontWeight.bold))),
                        Expanded(
                            child: Text('Time',
                                textAlign: TextAlign.center,
                                style: TextStyle(fontWeight: FontWeight.bold))),
                        Expanded(
                            child: Text('Attendance',
                                textAlign: TextAlign.right,
                                style: TextStyle(fontWeight: FontWeight.bold))),
                      ],
                    ),
                    const Divider(),
                    BlocBuilder<AttendanceBloc, AttendanceState>(
                      builder: (context, state) {
                        if (state is AttendanceLoading) {
                          return const Center(
                              child: CircularProgressIndicator());
                        } else if (state is AttendanceLoaded) {
                          return ListView.builder(
                            shrinkWrap: true,
                            physics: const NeverScrollableScrollPhysics(),
                                                      itemCount: state.attendanceRecords.length,
                                                      itemBuilder: (context, index) {
                                                        final record = state.attendanceRecords[index];
                                                        return Padding(
                                                          padding: const EdgeInsets.symmetric(vertical: 8.0),
                                                          child: Row(
                                                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                                            children: [
                                                              Expanded(child: Text(record.subject)),
                                                              Expanded(
                                                                  child: Text(record.time,
                                                                      textAlign: TextAlign.center)),
                                                              Expanded(
                                                                  child: Text(
                                                                record.status,
                                                                textAlign: TextAlign.right,
                                                                style: TextStyle(
                                                                    color: record.status == 'Present'
                                                                        ? Colors.green
                                                                        : record.status == 'Absent'
                                                                            ? Colors.red
                                                                            : Colors.orange),
                                                              )),
                                                            ],
                                                          ),
                                                        );
                                                      },                          );
                        } else if (state is AttendanceError) {
                          return Center(child: Text(state.message));
                        }
                        return const Center(
                            child: Text('Select a date to see attendance.'));
                      },
                    )
                  ],
                ),
              ),
            ),
            Card(
              margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
              elevation: 5,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(15),
              ),
              child: Padding(
                padding: const EdgeInsets.all(10.0),
                child: Column(
                  children: [
                    TableCalendar(
                      firstDay: DateTime.utc(2020, 1, 1),
                      lastDay: DateTime.utc(2030, 12, 31),
                      focusedDay: _focusedDay,
                      availableCalendarFormats: const {
                        CalendarFormat.month: 'Month',
                      },
                      selectedDayPredicate: (day) {
                        return isSameDay(_selectedDay, day);
                      },
                      onDaySelected: (selectedDay, focusedDay) {
                        setState(() {
                          _selectedDay = selectedDay;
                          _focusedDay = focusedDay;
                        });
                        context
                            .read<AttendanceBloc>()
                            .add(FetchAttendanceData(_selectedDay!));
                      },
                    ),
                    const SizedBox(height: 10),
                    const Wrap(
                      spacing: 10,
                      runSpacing: 10,
                      alignment: WrapAlignment.center,
                      children: [
                        _LegendItem(color: Colors.green, text: 'Present'),
                        _LegendItem(color: Colors.red, text: 'Absent'),
                        _LegendItem(color: Colors.orange, text: 'Late'),
                        _LegendItem(color: Colors.blue, text: 'Half Day'),
                        _LegendItem(color: Colors.grey, text: 'Holiday'),
                      ],
                    )
                  ],
                ),
              ),
            )
          ],
        ),
      ),
    );
  }
}

class _LegendItem extends StatelessWidget {
  final Color color;
  final String text;

  const _LegendItem({required this.color, required this.text});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        Container(
          width: 16,
          height: 16,
          decoration: BoxDecoration(
            color: color,
            shape: BoxShape.circle,
          ),
        ),
        const SizedBox(width: 5),
        Text(text),
      ],
    );
  }
}
