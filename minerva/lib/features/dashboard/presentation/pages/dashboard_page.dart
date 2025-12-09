import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:minerva_flutter/features/dashboard/presentation/bloc/dashboard_bloc.dart';
import 'package:minerva_flutter/features/dashboard/domain/entities/module.dart';
import 'package:minerva_flutter/utils/constants.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:developer'; // Import for logging

class DashboardPage extends StatefulWidget {
  const DashboardPage({Key? key}) : super(key: key);

  @override
  State<DashboardPage> createState() => _DashboardPageState();
}

class _DashboardPageState extends State<DashboardPage> {
  Color primaryColour = Colors.blue; // Default color
  Color secondaryColour = Colors.blue; // Default color
  String userName = 'Guest';
  String userImage = 'assets/placeholder_user.png';
  String classSection = '';
  String gender = 'unknown';

  @override
  void initState() {
    super.initState();
    _loadDashboardData();
    context.read<DashboardBloc>().add(FetchDashboardModules());
  }

  Future<void> _loadDashboardData() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      // Safely retrieve colors, falling back to default if null
      primaryColour = Color(int.parse((prefs.getString('primaryColour') ?? Constants.defaultPrimaryColour).replaceAll('#', '0xff')));
      secondaryColour = Color(int.parse((prefs.getString('secondaryColour') ?? Constants.defaultSecondaryColour).replaceAll('#', '0xff')));

      userName = prefs.getString(Constants.userName) ?? 'Guest'; // Use userName directly
      gender = prefs.getString(Constants.gender) ?? 'unknown'; // Retrieve gender
      String? apiUserImage = prefs.getString(Constants.userImage);

      if (apiUserImage != null && (apiUserImage.startsWith('http') || apiUserImage.startsWith('https'))) {
        userImage = apiUserImage;
      } else {
        // Fallback to gender-specific local assets if API image is local path or null
        if (gender == 'female') {
          userImage = 'assets/default_female.jpg';
        } else {
          userImage = 'assets/default_image.jpg'; // Generic or male placeholder
        }
      }

      classSection = prefs.getString(Constants.classSection) ?? '';
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Dashboard'),
        backgroundColor: secondaryColour, // Apply secondary color to AppBar
      ),
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            DrawerHeader(
              decoration: BoxDecoration(
                color: primaryColour, // Apply primary color to DrawerHeader
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  CircleAvatar(
                    radius: 30,
                    backgroundImage: userImage.startsWith('http') || userImage.startsWith('https')
                        ? NetworkImage(userImage)
                        : AssetImage(userImage) as ImageProvider,
                  ),
                  SizedBox(height: 10),
                  Text(
                    userName,
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 20,
                    ),
                  ),
                  Text(
                    classSection,
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 14,
                    ),
                  ),
                ],
              ),
            ),
            ListTile(
              leading: Icon(Icons.home),
              title: Text('Home'),
              onTap: () {
                // Navigate to Home - currently stays on dashboard
                Navigator.pop(context); // Close the drawer
              },
            ),
            ListTile(
              leading: Icon(Icons.person),
              title: Text('Profile'),
              onTap: () {
                context.push('/profile'); // Changed to push to allow popping back
                Navigator.pop(context); // Close the drawer
              },
            ),
            ListTile(
              leading: Icon(Icons.info),
              title: Text('About'),
              onTap: () {
                context.push('/about_school'); // Navigate to About School
                Navigator.pop(context); // Close the drawer
              },
            ),
            ListTile(
              leading: Icon(Icons.settings),
              title: Text('Setting'),
              onTap: () {
                // Navigate to Setting
                Navigator.pop(context); // Close the drawer
              },
            ),
            ListTile(
              leading: Icon(Icons.logout),
              title: Text('Logout'),
              onTap: () {
                // Handle Logout
                Navigator.pop(context); // Close the drawer
              },
            ),
          ],
        ),
      ),
      body: BlocBuilder<DashboardBloc, DashboardState>(
        builder: (context, state) {
          if (state is DashboardLoading) {
            log('DashboardPage - State: DashboardLoading');
            return const Center(child: CircularProgressIndicator());
          } else if (state is DashboardLoaded) {
            log('DashboardPage - State: DashboardLoaded');
            log('DashboardPage - elearningModules count: ${state.elearningModules.length}');
            log('DashboardPage - academicModules count: ${state.academicModules.length}');
            log('DashboardPage - communicateModules count: ${state.communicateModules.length}');
            log('DashboardPage - otherModules count: ${state.otherModules.length}');
            return SingleChildScrollView(
              child: Column(
                children: [
                  _buildModuleSection('E-Learning', state.elearningModules),
                  _buildModuleSection('Academic', state.academicModules),
                  _buildModuleSection('Communicate', state.communicateModules),
                  _buildModuleSection('Other', state.otherModules),
                ],
              ),
            );
          } else if (state is DashboardError) {
            log('DashboardPage - State: DashboardError - ${state.message}');
            return Center(child: Text(state.message));
          }
          log('DashboardPage - State: Initial or unknown');
          return const Center(child: Text('Dashboard Page Content (Initial)'));
        },
      ),
    );
  }

  void _onModuleTapped(Module module) {
    switch (module.name.toLowerCase()) {
      case 'fees':
        context.push('/fees');
        break;
      case 'notice board': // New case for Notice Board
        context.push('/notice_board');
        break;
      case 'apply leave': // New case for Apply Leave
        context.push('/apply_leave');
        break;
      case 'transport routes': // New case for Transport Routes
        context.push('/transport_routes');
        break;
      default:
        // Do nothing for now
        break;
    }
  }

  Widget _buildModuleSection(String title, List<Module> modules) {
    if (modules.isEmpty) {
      return const SizedBox.shrink(); // Hide section if no modules
    }
    log('Building section "$title" with ${modules.length} modules.'); // Log module count per section
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Text(
            title,
            style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
        ),
        Card( // New Card to group GridView
          margin: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 4.0), // Add margin around the card
          elevation: 2.0,
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8.0)), // Optional: rounded corners
          child: Padding(
            padding: const EdgeInsets.all(8.0), // Padding inside the card
            child: GridView.builder(
              shrinkWrap: true,
              physics: const NeverScrollableScrollPhysics(),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 4,
                crossAxisSpacing: 8.0, // Horizontal spacing between tiles
                mainAxisSpacing: 8.0, // Vertical spacing between tiles
              ),
              itemCount: modules.length,
              itemBuilder: (context, index) {
                final module = modules[index];
                return GestureDetector(
                  onTap: () => _onModuleTapped(module),
                  child: Column( // Individual tile content
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Image.asset(module.icon, width: 30, height: 30), // Fixed icon size
                      const SizedBox(height: 8),
                      Text(
                        module.name,
                        textAlign: TextAlign.center, // Center align text
                      ),
                    ],
                  ),
                );
              },
            ),
          ),
        ),
      ],
    );
  }
}
