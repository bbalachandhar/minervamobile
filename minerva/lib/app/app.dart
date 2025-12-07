import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:minerva_flutter/features/about_school/data/repositories/about_school_repository.dart';
import 'package:minerva_flutter/features/about_school/presentation/bloc/about_school_bloc.dart';
import 'package:minerva_flutter/features/about_school/presentation/pages/about_school_page.dart';
import 'package:minerva_flutter/features/auth/data/repositories/authentication_repository.dart';
import 'package:minerva_flutter/features/auth/presentation/bloc/auth_bloc.dart';
import 'package:minerva_flutter/features/auth/presentation/pages/login_page.dart';
import 'package:minerva_flutter/features/dashboard/data/repositories/dashboard_repository.dart';
import 'package:minerva_flutter/features/dashboard/presentation/bloc/dashboard_bloc.dart';
import 'package:minerva_flutter/features/dashboard/presentation/pages/dashboard_page.dart';
import 'package:minerva_flutter/features/fees/data/repositories/fees_repository.dart';
import 'package:minerva_flutter/features/fees/presentation/bloc/fees_bloc.dart';
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
import 'package:shared_preferences/shared_preferences.dart';

import '../features/fees/presentation/pages/offline_payment_screen.dart';

class App extends StatelessWidget {
  final SharedPreferences sharedPreferences;

  App({Key? key, required this.sharedPreferences}) : super(key: key);

  late final GoRouter _router = GoRouter(
    initialLocation: sharedPreferences.getString('base_url') == null ? '/url' : '/splash',
    routes: <GoRoute>[
      GoRoute(
        path: '/url',
        builder: (BuildContext context, GoRouterState state) {
          return const UrlPage();
        },
      ),
      GoRoute(
        path: '/splash',
        builder: (BuildContext context, GoRouterState state) {
          return const SplashPage();
        },
      ),
      GoRoute(
        path: '/login',
        builder: (BuildContext context, GoRouterState state) {
          return const LoginPage();
        },
      ),
      GoRoute(
        path: '/dashboard',
        builder: (BuildContext context, GoRouterState state) {
          return const DashboardPage();
        },
      ),
      GoRoute(
        path: '/profile', // New route for ProfilePage
        builder: (BuildContext context, GoRouterState state) {
          return const ProfilePage();
        },
      ),
      GoRoute(
        path: '/about_school', // New route for AboutSchoolPage
        builder: (BuildContext context, GoRouterState state) {
          return const AboutSchoolPage();
        },
      ),
      GoRoute(
        path: '/fees', // New route for FeesScreen
        builder: (BuildContext context, GoRouterState state) {
          return FeesScreen();
        },
      ),
      GoRoute(
        path: '/fees/processing',
        builder: (BuildContext context, GoRouterState state) {
          return ProcessingFeesScreen();
        },
      ),
      GoRoute(
        path: '/fees/offline',
        builder: (BuildContext context, GoRouterState state) {
          return OfflinePaymentScreen();
        },
      ),
    ],
  );

  @override
  Widget build(BuildContext context) {
    return MultiRepositoryProvider(
      providers: [
        RepositoryProvider<AuthenticationRepository>(
          create: (context) => AuthenticationRepository(sharedPreferences: sharedPreferences),
        ),
        RepositoryProvider<SettingsRepository>(
          create: (context) => SettingsRepository(sharedPreferences: sharedPreferences),
        ),
        RepositoryProvider<DashboardRepository>(
          create: (context) => DashboardRepository(sharedPreferences: sharedPreferences),
        ),
        RepositoryProvider<ProfileRepository>(
          create: (context) => ProfileRepository(sharedPreferences: sharedPreferences),
        ),
        RepositoryProvider<AboutSchoolRepository>(
          create: (context) => AboutSchoolRepository(sharedPreferences: sharedPreferences),
        ),
        RepositoryProvider<FeesRepository>(
          create: (context) => FeesRepository(sharedPreferences: sharedPreferences),
        ),
      ],
      child: MultiBlocProvider(
        providers: [
          BlocProvider(
            create: (context) => AuthBloc(
              authenticationRepository:
                  RepositoryProvider.of<AuthenticationRepository>(context),
            ),
          ),
          BlocProvider(
            create: (context) => UrlBloc(
              settingsRepository:
                  RepositoryProvider.of<SettingsRepository>(context),
            ),
          ),
          BlocProvider(
            create: (context) => DashboardBloc(
              dashboardRepository:
                  RepositoryProvider.of<DashboardRepository>(context),
            ),
          ),
          BlocProvider(
            create: (context) => ProfileBloc(
              profileRepository:
                  RepositoryProvider.of<ProfileRepository>(context),
            ),
          ),
          BlocProvider(
            create: (context) => AboutSchoolBloc(
              aboutSchoolRepository:
                  RepositoryProvider.of<AboutSchoolRepository>(context),
            ),
          ),
          BlocProvider(
            create: (context) => FeesBloc(
              feesRepository:
                  RepositoryProvider.of<FeesRepository>(context),
            ),
          ),
          BlocProvider(
            create: (context) => ProcessingFeesBloc(
              feesRepository:
                  RepositoryProvider.of<FeesRepository>(context),
            ),
          ),
          BlocProvider(
            create: (context) => OfflinePaymentBloc(
              feesRepository:
                  RepositoryProvider.of<FeesRepository>(context),
            ),
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
