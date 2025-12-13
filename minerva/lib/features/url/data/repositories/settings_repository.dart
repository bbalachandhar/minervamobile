import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:minerva_flutter/utils/constants.dart';
import 'dart:developer';

class SettingsRepository {
  final SharedPreferences sharedPreferences;

  SettingsRepository({required this.sharedPreferences});

  Future<void> validateUrl(String baseUrl) async {
    log('SettingsRepository: validateUrl started with baseUrl: $baseUrl');
    if (baseUrl.isEmpty) {
      log('SettingsRepository: baseUrl is empty');
      throw Exception('URL cannot be empty');
    }
    
    if (!baseUrl.endsWith('/')) {
      baseUrl += '/';
    }
    final url = Uri.parse('${baseUrl}app');
    log('SettingsRepository: Validation URL: $url');

    try {
      final response = await http.post(url);
      log('SettingsRepository: Received response with statusCode: ${response.statusCode}');
      log('SettingsRepository: Response body: ${response.body}');

      if (response.statusCode == 200) {
        final body = json.decode(response.body);
        log('SettingsRepository: API Response: $body');
        
        String responseApiUrl = body['url'] ?? '';
        String responseSiteUrl = body['site_url'] ?? '';

        if (responseApiUrl.isNotEmpty && !responseApiUrl.endsWith('/')) {
          responseApiUrl += '/';
        }
        if (responseSiteUrl.isNotEmpty && !responseSiteUrl.endsWith('/')) {
          responseSiteUrl += '/';
        }
        if (responseSiteUrl.endsWith('/api/')) {
          responseSiteUrl = responseSiteUrl.substring(0, responseSiteUrl.length - 5);
          if (!responseSiteUrl.endsWith('/')) {
            responseSiteUrl += '/';
          }
        }
        
        await sharedPreferences.setString(Constants.apiUrl, responseApiUrl);
        await sharedPreferences.setString(Constants.imagesUrl, responseSiteUrl);
        await sharedPreferences.setString(Constants.appVer, body['app_ver']);
        log('SettingsRepository: Stored apiUrl, imagesUrl, appVer');


        final appLogo = body['app_logo'];
        log('SettingsRepository: app_logo from API: $appLogo');
        if (appLogo != null && appLogo.isNotEmpty) {
          await sharedPreferences.setString(Constants.appLogo, responseSiteUrl + "uploads/school_content/logo/app_logo/" + appLogo);
          log('SettingsRepository: Stored appLogo');
        } else {
          await sharedPreferences.setString(Constants.appLogo, '');
          log('SettingsRepository: appLogo set to empty');
        }

        final secColour = body['app_secondary_color_code'];
        final primaryColour = body['app_primary_color_code'];

        if(secColour != null && primaryColour != null && secColour.length == 7 && primaryColour.length == 7 ) {
          await sharedPreferences.setString('secondaryColour', secColour);
          await sharedPreferences.setString('primaryColour', primaryColour);
          log('SettingsRepository: Stored custom colors');
        } else {
          await sharedPreferences.setString('secondaryColour', Constants.defaultSecondaryColour);
          await sharedPreferences.setString('primaryColour', Constants.defaultPrimaryColour);
          log('SettingsRepository: Stored default colors');
        }
        log('SettingsRepository: validateUrl completed successfully');
        return;
      } else {
        log('SettingsRepository: API call failed with status code: ${response.statusCode}');
        throw Exception('Failed to connect to the server. Status code: ${response.statusCode}');
      }
    } catch (e) {
      log('SettingsRepository: Error during validateUrl: $e');
      rethrow; // Re-throw the exception after logging it
    }
  }

  String getAppLogo() {
    return sharedPreferences.getString(Constants.appLogo) ?? '';
  }
}
