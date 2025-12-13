import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:developer'; // Added this import

import 'package:minerva_flutter/features/about_school/data/repositories/about_school_repository.dart';
import 'package:minerva_flutter/features/about_school/presentation/bloc/about_school_bloc.dart';
import 'package:minerva_flutter/features/about_school/presentation/pages/about_school_page.dart';
import 'package:minerva_flutter/features/auth/data/repositories/authentication_repository.dart';
import 'package:minerva_flutter/features/auth/presentation/bloc/auth_bloc.dart';
import 'package:minerva_flutter/features/auth/presentation/pages/login_page.dart';
import 'package:minerva_flutter/features/dashboard/data/repositories/dashboard_repository.dart';
import 'package:minerva_flutter/features/dashboard/presentation/bloc/dashboard_bloc.dart';
import 'package:minerva_flutter/features/dashboard/presentation/pages/dashboard_page.dart';
import 'package:minerva_flutter/features/fees/presentation/pages/fees_screen.dart';
import 'package:minerva_flutter/features/fees/presentation/pages/processing_fees_screen.dart';
import 'package:minerva_flutter/features/fees/presentation/bloc/offline_payment_bloc.dart';
import 'package:minerva_flutter/features/fees/presentation/bloc/processing_fees_bloc.dart';
import 'package:minerva_flutter/features/profile/data/repositories/profile_repository.dart';
import 'package:minerva_flutter/features/profile/presentation/bloc/profile_bloc.dart';
import 'package:minerva_flutter/features/profile/presentation/pages/profile_page.dart';
import 'package:minerva_flutter/features/splash/presentation/pages/splash_page.dart';
import 'package:minerva_flutter/features/url/data/repositories/settings_repository.dart';
import 'package:minerva_flutter/features/url/presentation/bloc/url_bloc.dart';
import 'package:minerva_flutter/features/url/presentation/pages/url_page.dart';
import 'package:minerva_flutter/features/fees/presentation/pages/offline_payment_screen.dart';

// NEW IMPORTS FOR NOTICE FEATURE
import 'package:minerva_flutter/features/notice/data/repositories/notice_repository_impl.dart';
import 'package:minerva_flutter/features/notice/domain/repositories/notice_repository.dart';
import 'package:minerva_flutter/features/notice/domain/usecases/get_notices_usecase.dart';
import 'package:minerva_flutter/features/notice/presentation/bloc/notice_bloc.dart';
import 'package:minerva_flutter/features/notice/presentation/pages/notice_page.dart';

// Imports for Apply Leave Feature
import 'package:minerva_flutter/features/apply_leave/data/repositories/leave_repository_impl.dart';
import 'package:minerva_flutter/features/apply_leave/domain/repositories/leave_repository.dart';
import 'package:minerva_flutter/features/apply_leave/domain/usecases/get_leave_applications_usecase.dart';
import 'package:minerva_flutter/features/apply_leave/domain/usecases/delete_leave_application_usecase.dart';
import 'package:minerva_flutter/features/apply_leave/presentation/bloc/apply_leave_bloc.dart';
import 'package:minerva_flutter/features/apply_leave/presentation/pages/apply_leave_page.dart';
import 'package:minerva_flutter/features/apply_leave/presentation/pages/add_leave_page.dart';
import 'package:minerva_flutter/features/apply_leave/presentation/pages/edit_leave_page.dart';
import 'package:minerva_flutter/features/apply_leave/domain/entities/leave_application_entity.dart';

import 'package:minerva_flutter/features/apply_leave/domain/usecases/edit_leave_application_usecase.dart';
import 'package:minerva_flutter/features/apply_leave/domain/usecases/submit_leave_application_usecase.dart';
import 'package:minerva_flutter/features/transport_route/data/repositories/transport_route_repository.dart';
import 'package:minerva_flutter/features/transport_route/presentation/bloc/transport_route_bloc.dart';
import 'package:minerva_flutter/features/transport_route/presentation/pages/transport_route_page.dart';
import 'package:minerva_flutter/features/visitor_book/data/repositories/visitor_repository.dart';
import 'package:minerva_flutter/features/visitor_book/presentation/bloc/visitor_bloc.dart';
import 'package:minerva_flutter/features/visitor_book/presentation/pages/visitor_book_page.dart';
import 'package:minerva_flutter/features/visitor_book/presentation/pages/document_viewer_page.dart';
import 'package:minerva_flutter/features/hostel/presentation/pages/hostel_rooms_page.dart';
import 'package:minerva_flutter/features/hostel/data/repositories/hostel_repository_impl.dart';
import 'package:minerva_flutter/features/hostel/domain/repositories/hostel_repository.dart';
import 'package:minerva_flutter/features/hostel/domain/usecases/get_hostel_rooms_usecase.dart';
import 'package:minerva_flutter/features/hostel/presentation/bloc/hostel_bloc.dart';
import 'package:minerva_flutter/features/calendar_todo/data/repositories/calendar_todo_repository_impl.dart';
import 'package:minerva_flutter/features/calendar_todo/domain/repositories/calendar_todo_repository.dart';
import 'package:minerva_flutter/features/calendar_todo/domain/usecases/create_calendar_todo_usecase.dart';
import 'package:minerva_flutter/features/calendar_todo/domain/usecases/delete_calendar_todo_usecase.dart';
import 'package:minerva_flutter/features/calendar_todo/domain/usecases/get_calendar_todos_usecase.dart';
import 'package:minerva_flutter/features/calendar_todo/domain/usecases/mark_calendar_todo_as_complete_usecase.dart';
import 'package:minerva_flutter/features/calendar_todo/domain/usecases/update_calendar_todo_usecase.dart';
import 'package:minerva_flutter/features/calendar_todo/presentation/bloc/calendar_todo_bloc.dart';
import 'package:minerva_flutter/features/calendar_todo/presentation/pages/calendar_todo_page.dart';
import 'package:minerva_flutter/features/library/data/repositories/library_repository_impl.dart';
import 'package:minerva_flutter/features/library/domain/repositories/library_repository.dart';
import 'package:minerva_flutter/features/library/domain/usecases/get_available_books_usecase.dart';
import 'package:minerva_flutter/features/library/domain/usecases/get_issued_books_usecase.dart';
import 'package:minerva_flutter/features/teachers_rating/data/repositories/teacher_repository.dart';
import 'package:minerva_flutter/features/library/presentation/bloc/library_bloc.dart';
import 'package:minerva_flutter/features/library/presentation/pages/library_page.dart';
import 'package:minerva_flutter/features/teachers_rating/presentation/pages/teachers_rating_page.dart';

import 'package:minerva_flutter/features/staff_profile/data/repositories/staff_profile_repository.dart';
import 'package:minerva_flutter/features/staff_profile/presentation/bloc/staff_profile_bloc.dart';
import 'package:minerva_flutter/features/staff_profile/presentation/pages/staff_profile_page.dart';

import 'package:minerva_flutter/features/timetable/data/datasources/timetable_remote_data_source.dart';
import 'package:minerva_flutter/features/timetable/data/repositories/timetable_repository_impl.dart';
import 'package:minerva_flutter/features/timetable/domain/repositories/timetable_repository.dart';
import 'package:minerva_flutter/features/timetable/domain/usecases/get_timetable_usecase.dart';
import 'package:minerva_flutter/features/timetable/presentation/bloc/timetable_bloc.dart';
import 'package:minerva_flutter/features/timetable/presentation/pages/timetable_page.dart';

import 'package:minerva_flutter/features/syllabus_status/data/datasources/syllabus_status_remote_data_source.dart';
import 'package:minerva_flutter/features/syllabus_status/data/repositories/syllabus_status_repository_impl.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/repositories/syllabus_status_repository.dart';
import 'package:minerva_flutter/features/syllabus_status/domain/usecases/get_syllabus_status_usecase.dart';
import 'package:minerva_flutter/features/syllabus_status/presentation/bloc/syllabus_status_bloc.dart';
import 'package:minerva_flutter/features/syllabus_status/presentation/pages/syllabus_status_page.dart';

// Imports for Attendance Feature
import 'package:minerva_flutter/features/attendance/data/datasources/attendance_remote_data_source.dart';
import 'package:minerva_flutter/features/attendance/data/repositories/attendance_repository_impl.dart';
import 'package:minerva_flutter/features/attendance/domain/repositories/attendance_repository.dart';
import 'package:minerva_flutter/features/attendance/domain/usecases/get_attendance.dart';
import 'package:minerva_flutter/features/attendance/presentation/bloc/attendance_bloc.dart';
import 'package:minerva_flutter/features/attendance/presentation/pages/attendance_page.dart';

import '../features/dashboard/presentation/pages/staff_dashboard_page.dart';
import '../features/fees/data/repositories/fees_repository.dart';
import '../features/fees/presentation/bloc/fees_bloc.dart';



class App extends StatelessWidget {
  final SharedPreferences sharedPreferences;

  App({Key? key, required this.sharedPreferences}) : super(key: key) {
    log('App: App widget constructor called');
  }

  late final GoRouter _router = GoRouter(
    initialLocation: sharedPreferences.getString('base_url') == null ? '/url' : '/splash',
    routes: <GoRoute>[
      GoRoute(
        path: '/url',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /url');
          return const UrlPage();
        },
      ),
      GoRoute(
        path: '/splash',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /splash');
          return const SplashPage();
        },
      ),
      GoRoute(
        path: '/login',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /login');
          return const LoginPage();
        },
      ),
      GoRoute(
        path: '/dashboard',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /dashboard');
          return const DashboardPage();
        },
      ),
      GoRoute(
        path: '/staff/dashboard',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /staff/dashboard');
          return const StaffDashboardPage();
        },
      ),
      GoRoute(
        path: '/staff/profile',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /staff/profile');
          return const StaffProfilePage();
        },
      ),
      GoRoute(
        path: '/profile', // New route for ProfilePage
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /profile');
          return const ProfilePage();
        },
      ),
      GoRoute(
        path: '/about_school', // New route for AboutSchoolPage
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /about_school');
          return const AboutSchoolPage();
        },
      ),
      GoRoute(
        path: '/fees', // New route for FeesScreen
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /fees');
          return FeesScreen();
        },
      ),
      GoRoute(
        path: '/fees/processing',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /fees/processing');
          return ProcessingFeesScreen();
        },
      ),
      GoRoute(
        path: '/fees/offline',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /fees/offline');
          return OfflinePaymentScreen();
        },
      ),
      GoRoute(
        path: '/notice_board', // New route for NoticePage
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /notice_board');
          return const NoticePage();
        },
      ),
      GoRoute(
        path: '/apply_leave',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /apply_leave');
          return const ApplyLeavePage();
        },
        routes: [
          GoRoute(
            path: 'add',
            builder: (BuildContext context, GoRouterState state) {
              log('App: Navigating to /apply_leave/add');
              return const AddLeavePage();
            },
          ),
          GoRoute(
            path: 'edit',
            builder: (BuildContext context, GoRouterState state) {
              log('App: Navigating to /apply_leave/edit');
              final leaveApplication = state.extra as LeaveApplicationEntity;
              return EditLeavePage(leaveApplication: leaveApplication);
            },
          ),
        ],
      ),
      GoRoute(
        path: '/transport_routes',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /transport_routes');
          return const TransportRoutePage();
        },
      ),
      GoRoute(
        path: '/visitor_book',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /visitor_book');
          return const VisitorBookPage();
        },
      ),
      GoRoute(
        path: '/document_viewer',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /document_viewer');
          final Map<String, dynamic> args = state.extra as Map<String, dynamic>;
          return DocumentViewerPage(
            documentUrl: args['documentUrl'],
            title: args['title'],
          );
        },
      ),
      GoRoute(
        path: '/hostel',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /hostel');
          return const HostelRoomsPage();
        },
      ),
      GoRoute(
        path: '/calendar_todo',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /calendar_todo');
          return const CalendarTodoPage();
        },
      ),
      GoRoute(
        path: '/library',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /library');
          return const LibraryPage();
        },
      ),
      GoRoute(
        path: '/teachers_rating',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /teachers_rating');
          return const TeachersRatingPage();
        },
      ),
      GoRoute(
        path: '/class_timetable', // New route for TimetablePage
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /class_timetable');
          return const TimetablePage();
        },
      ),
      GoRoute(
        path: '/syllabus_status', // New route for SyllabusStatusPage
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /syllabus_status');
          return const SyllabusStatusPage();
        },
      ),
      GoRoute(
        path: '/attendance',
        builder: (BuildContext context, GoRouterState state) {
          log('App: Navigating to /attendance');
          return const AttendancePage();
        },
      ),
    ],
  );

  @override
  Widget build(BuildContext context) {
    log('App: Building widget tree');
    return MultiRepositoryProvider(
      providers: [
        RepositoryProvider<AuthenticationRepository>(
          create: (context) {
            log('App: Creating AuthenticationRepository');
            return AuthenticationRepository(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<SettingsRepository>(
          create: (context) {
            log('App: Creating SettingsRepository');
            return SettingsRepository(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<DashboardRepository>(
          create: (context) {
            log('App: Creating DashboardRepository');
            return DashboardRepository(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<ProfileRepository>(
          create: (context) {
            log('App: Creating ProfileRepository');
            return ProfileRepository(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<AboutSchoolRepository>(
          create: (context) {
            log('App: Creating AboutSchoolRepository');
            return AboutSchoolRepository(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<FeesRepository>(
          create: (context) {
            log('App: Creating FeesRepository');
            return FeesRepository(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<NoticeRepository>(
          create: (context) {
            log('App: Creating NoticeRepository');
            return NoticeRepositoryImpl(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<LeaveRepository>(
          create: (context) {
            log('App: Creating LeaveRepository');
            return LeaveRepositoryImpl(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<TransportRouteRepository>(
          create: (context) {
            log('App: Creating TransportRouteRepository');
            return TransportRouteRepositoryImpl(
            sharedPreferences: sharedPreferences,
          );
          },
        ),
        RepositoryProvider<VisitorRepository>(
          create: (context) {
            log('App: Creating VisitorRepository');
            return VisitorRepositoryImpl(
            sharedPreferences: sharedPreferences,
          );
          },
        ),
        RepositoryProvider<HostelRepository>(
          create: (context) {
            log('App: Creating HostelRepository');
            return HostelRepositoryImpl(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<CalendarTodoRepository>(
          create: (context) {
            log('App: Creating CalendarTodoRepository');
            return CalendarTodoRepositoryImpl(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<LibraryRepository>(
          create: (context) {
            log('App: Creating LibraryRepository');
            return LibraryRepositoryImpl(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<TeacherRepository>(
          create: (context) {
            log('App: Creating TeacherRepository');
            return TeacherRepository();
          },
        ),
        RepositoryProvider<StaffProfileRepository>(
          create: (context) {
            log('App: Creating StaffProfileRepository');
            return StaffProfileRepository(sharedPreferences: sharedPreferences);
          },
        ),
        RepositoryProvider<TimetableRepository>(
          create: (context) {
            log('App: Creating TimetableRepository');
            return TimetableRepositoryImpl(
              remoteDataSource: TimetableRemoteDataSourceImpl(sharedPreferences: sharedPreferences),
            );
          },
        ),
        RepositoryProvider<SyllabusStatusRepository>(
          create: (context) {
            log('App: Creating SyllabusStatusRepository');
            return SyllabusStatusRepositoryImpl(
              remoteDataSource: SyllabusStatusRemoteDataSourceImpl(sharedPreferences: sharedPreferences),
            );
          },
        ),
        RepositoryProvider<http.Client>(
          create: (context) => http.Client(),
        ),
        RepositoryProvider<AttendanceRemoteDataSource>(
          create: (context) => AttendanceRemoteDataSourceImpl(
            client: RepositoryProvider.of<http.Client>(context),
            sharedPreferences: sharedPreferences,
          ),
        ),
        RepositoryProvider<AttendanceRepository>(
          create: (context) => AttendanceRepositoryImpl(
            remoteDataSource: RepositoryProvider.of<AttendanceRemoteDataSource>(context),
          ),
        ),
      ],
      child: MultiBlocProvider(
        providers: [
          BlocProvider(
            create: (context) {
              log('App: Creating AuthBloc');
              return AuthBloc(
              authenticationRepository:
                  RepositoryProvider.of<AuthenticationRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating UrlBloc');
              return UrlBloc(
              settingsRepository:
                  RepositoryProvider.of<SettingsRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating DashboardBloc');
              return DashboardBloc(
              dashboardRepository:
                  RepositoryProvider.of<DashboardRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating ProfileBloc');
              return ProfileBloc(
              profileRepository:
                  RepositoryProvider.of<ProfileRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating AboutSchoolBloc');
              return AboutSchoolBloc(
              aboutSchoolRepository:
                  RepositoryProvider.of<AboutSchoolRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating FeesBloc');
              return FeesBloc(
              feesRepository:
                  RepositoryProvider.of<FeesRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating ProcessingFeesBloc');
              return ProcessingFeesBloc(
              feesRepository:
                  RepositoryProvider.of<FeesRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating OfflinePaymentBloc');
              return OfflinePaymentBloc(
              feesRepository:
                  RepositoryProvider.of<FeesRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating NoticeBloc');
              return NoticeBloc(
              getNoticesUseCase: GetNoticesUseCase(
                repository: RepositoryProvider.of<NoticeRepository>(context),
              ),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating ApplyLeaveBloc');
              return ApplyLeaveBloc(
              getLeaveApplicationsUseCase: GetLeaveApplicationsUseCase(
                repository: RepositoryProvider.of<LeaveRepository>(context),
              ),
              deleteLeaveApplicationUseCase: DeleteLeaveApplicationUseCase(
                repository: RepositoryProvider.of<LeaveRepository>(context),
              ),
              submitLeaveApplicationUseCase: SubmitLeaveApplicationUseCase(
                repository: RepositoryProvider.of<LeaveRepository>(context),
              ),
              editLeaveApplicationUseCase: EditLeaveApplicationUseCase(
                repository: RepositoryProvider.of<LeaveRepository>(context),
              ),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating TransportRouteBloc');
              return TransportRouteBloc(
              transportRouteRepository: RepositoryProvider.of<TransportRouteRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating VisitorBloc');
              return VisitorBloc(
              visitorRepository: RepositoryProvider.of<VisitorRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating HostelBloc');
              return HostelBloc(
              getHostelRoomsUseCase: GetHostelRoomsUseCase(
                repository: RepositoryProvider.of<HostelRepository>(context),
              ),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating CalendarTodoBloc');
              return CalendarTodoBloc(
              getCalendarTodosUseCase: GetCalendarTodosUseCase(
                repository: RepositoryProvider.of<CalendarTodoRepository>(context),
              ),
              createCalendarTodoUseCase: CreateCalendarTodoUseCase(
                repository: RepositoryProvider.of<CalendarTodoRepository>(context),
              ),
              updateCalendarTodoUseCase: UpdateCalendarTodoUseCase(
                repository: RepositoryProvider.of<CalendarTodoRepository>(context),
              ),
              deleteCalendarTodoUseCase: DeleteCalendarTodoUseCase(
                repository: RepositoryProvider.of<CalendarTodoRepository>(context),
              ),
              markCalendarTodoAsCompleteUseCase: MarkCalendarTodoAsCompleteUseCase(
                repository: RepositoryProvider.of<CalendarTodoRepository>(context),
              ),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating LibraryBloc');
              return LibraryBloc(
              getAvailableBooksUseCase: GetAvailableBooksUseCase(
                repository: RepositoryProvider.of<LibraryRepository>(context),
              ),
              getIssuedBooksUseCase: GetIssuedBooksUseCase(
                repository: RepositoryProvider.of<LibraryRepository>(context),
              ),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating StaffProfileBloc');
              return StaffProfileBloc(
              staffProfileRepository:
                  RepositoryProvider.of<StaffProfileRepository>(context),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating TimetableBloc');
              return TimetableBloc(
              getTimetableUseCase: GetTimetableUseCase(
                repository: RepositoryProvider.of<TimetableRepository>(context),
              ),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating SyllabusStatusBloc');
              return SyllabusStatusBloc(
              getSyllabusStatusUseCase: GetSyllabusStatusUseCase(
                repository: RepositoryProvider.of<SyllabusStatusRepository>(context),
              ),
            );
            },
          ),
          BlocProvider(
            create: (context) {
              log('App: Creating AttendanceBloc');
              return AttendanceBloc(
                getAttendance: GetAttendance(
                  RepositoryProvider.of<AttendanceRepository>(context),
                ),
              );
            },
          ),
        ],
        child: MaterialApp.router(
          routerConfig: _router,
          title: 'Minerva Flutter',
          theme: ThemeData(
            primarySwatch: Colors.blue,
          ),
        ),
      ),
    );
  }
}



