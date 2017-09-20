
[![official JetBrains project](http://jb.gg/badges/official-plastic.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# TeamCity Achievements plugin

This plugin grants achievements to TeamCity users for different actions.

Download
-----------------

The latest build of the plugin can be downloaded from the [public TeamCity server](http://teamcity.jetbrains.com/repository/download/TeamCityPluginsByJetBrains_TeamcityAchievements_Build/.lastPinned/achievements.zip).

How To Build
-----------------

Run `maven clean package`

How To Install
-----------------

Install the plugin as described in the [TeamCity documentation]( http://confluence.jetbrains.com/display/TCDL/Installing+Additional+Plugins).

After successful installation the "My Achievements" tab becomes visible on the user profile page.

Available achievements
-----------------

Available achievements can be seen on "My Achievements" tab of the user profile page. At the moment the following achievements are available:

* Cleanup Booster - Granted for unpinning several builds in a row. This will help cleanup process to free more disk space on the server.
* Saboteur 	      - Granted for bombing (muting) several tests with automatic explosion (unmute) on a specific date.
* Sapper 	        - Granted for disarming (unmuting) several tests.
* Crusher 	      - Granted if a person is assigned investigation for a series of broken compilations.
* Debugger 	      - Granted for a series of commits mentioning fixed bugs.
* Big Brother 	  - Granted for assigning investigations to several different persons.
* Taxonomist 	    - Granted for tagging several builds in a row.
* Novelist 	      - Granted for extra long commit descriptions.
* Boy Scout 	    - Granted for assigning several investigations for a test or problem to himself.
* Early Bird 	    - Granted for some early action on the build server.


Author
-----------------

Pavel Sher (pavel.sher@gmail.com)

