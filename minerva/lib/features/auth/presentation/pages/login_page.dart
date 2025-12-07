import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:minerva_flutter/features/auth/presentation/bloc/auth_bloc.dart';
import 'package:minerva_flutter/features/url/data/repositories/settings_repository.dart'; // Import SettingsRepository
import 'package:minerva_flutter/utils/constants.dart'; // Import Constants
import 'package:shared_preferences/shared_preferences.dart'; // Import SharedPreferences

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final ValueNotifier<bool> _isPasswordVisible = ValueNotifier<bool>(false);
  String? appLogoUrl;

  @override
  void initState() {
    super.initState();
    _loadAppLogo();
    // Prefill username and password for testing
    _usernameController.text = 'std60';
    _passwordController.text = 'ou11za';
  }

  Future<void> _loadAppLogo() async {
    final sharedPreferences = await SharedPreferences.getInstance();
    setState(() {
      appLogoUrl = sharedPreferences.getString(Constants.appLogo);
    });
  }

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    _isPasswordVisible.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: BlocConsumer<AuthBloc, AuthState>(
        listener: (context, state) {
          if (state is AuthSuccess) {
            context.go('/dashboard');
          } else if (state is AuthFailure) {
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text(state.error)),
            );
          }
        },
        builder: (context, state) {
          return Container(
            decoration: const BoxDecoration(
              image: DecorationImage(
                image: AssetImage("assets/img_login_background.png"),
                fit: BoxFit.cover,
              ),
            ),
            child: Center(
              child: SingleChildScrollView(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    // App Logo
                    if (appLogoUrl != null && appLogoUrl!.isNotEmpty)
                      Image.network(
                        appLogoUrl!,
                        height: 100,
                      )
                    else
                      Image.asset(
                        'assets/logo.png', // Fallback to local asset
                        height: 100,
                      ),
                    const SizedBox(height: 50),
                    TextField(
                      controller: _usernameController,
                      decoration: const InputDecoration(
                        labelText: 'Username',
                        border: OutlineInputBorder(),
                         filled: true,
                        fillColor: Colors.white,
                        prefixIcon: ImageIcon(AssetImage('assets/ic_user.png')),
                      ),
                    ),
                    const SizedBox(height: 20),
                    ValueListenableBuilder<bool>(
                      valueListenable: _isPasswordVisible,
                      builder: (context, isVisible, child) {
                        return TextField(
                          controller: _passwordController,
                          obscureText: !isVisible,
                          decoration: InputDecoration(
                            labelText: 'Password',
                            border: const OutlineInputBorder(),
                            filled: true,
                            fillColor: Colors.white,
                            prefixIcon: const ImageIcon(AssetImage('assets/ic_lock_filled.png')),
                            suffixIcon: IconButton(
                              icon: Image.asset(
                                isVisible ? 'assets/eye_black.png' : 'assets/eyehide.png',
                                height: 24,
                                width: 24,
                              ),
                              onPressed: () {
                                _isPasswordVisible.value = !isVisible;
                              },
                            ),
                          ),
                        );
                      },
                    ),
                    const SizedBox(height: 20),
                    ElevatedButton(
                      onPressed: state is AuthLoading
                          ? null
                          : () {
                              context.read<AuthBloc>().add(
                                    LoginRequested(
                                      username: _usernameController.text,
                                      password: _passwordController.text,
                                    ),
                                  );
                            },
                      style: ElevatedButton.styleFrom(
                        minimumSize: const Size(double.infinity, 50), // Full width button
                      ),
                      child: state is AuthLoading
                          ? const CircularProgressIndicator(color: Colors.white)
                          : const Text('Login'),
                    ),
                    const SizedBox(height: 20),
                    TextButton(
                      onPressed: () {
                        // Navigate to Forgot Password
                      },
                      child: const Text('Forgot Password?'),
                    ),
                    const SizedBox(height: 10),
                    TextButton(
                      onPressed: () {
                        // Navigate to Change URL
                      },
                      child: const Text('Change URL'),
                    ),
                    const SizedBox(height: 10),
                    TextButton(
                      onPressed: () {
                        // Navigate to Privacy Policy
                      },
                      child: const Text('Privacy Policy'),
                    ),
                  ],
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}