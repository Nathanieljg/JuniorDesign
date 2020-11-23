# JIC-134 Twist-On-Time

## Release Notes ATAK Stand-Alone
### Version 1.0.0
### New Features
* Developed stand-alone ATAK application
* Allowed users to create an alarm or countdown timer
* Allowed users to view a progress bar for the time remaining on a timer
* Allowed users to add early warning notifications for a timer
* Allowed users to view a list of existing timers
* Allowed users to view a list of existing early warning notifications

### Bug Fixes
* Developed an alarm list for persistence (saving state when exiting the application)
* Completed alarm manager conversion/integration
* Attempted integration with original ATAK application repository (due to missing code needed to stop effort for now)

### Known Bugs
* Application appears to only run while the SDK (i.e. Android device) is sleeping, will not run when the device is turned off (i.e. rebooted)
* After a user edits the countdown by adding an early warning notification, they are brought back to the view timers screen where the countdown has reset to the originally set time (rather than running while editing, it will reset)
* Appears to be a 2 second delay between the early warning notification sound and the time left on timer (having notifications seconds apart will be an issue)


## Installation Guide

### Prerequisites
The following pre requisites are necessary to run this application.
* JDK 1.8
* Android SDK
    * Target SDK Version: 29
    * Minimum SDK Version: 23
* Generating Signed APK
    * From Android Studio:
        1. ***Build*** menu
        2. ***Generate Signed APK...***
        3. Fill in the keystore information (you only need to do this once manually and then let Android Studio remember it)

### Setting Up Environment
* Download and install Android Studio
    * [https://developer.android.com/studio](https://developer.android.com/studio)

### Dependencies
* Download Android version 10, API 29 or higher.
    * See for more information: [https://developer.android.com/studio/releases/platforms](https://developer.android.com/studio/releases/platforms)

### Download
Clone this repository and import into **Android Studio**. Run the following statement directly from the command line:

`git clone https://github.com/Nathanieljg/JuniorDesign.git`

OR

Download the repository as a .zip file and extract the file to a desired location on your local machine. Repository can be downloaded with [https://github.com/Nathanieljg/JuniorDesign/archive/master.zip](https://github.com/Nathanieljg/JuniorDesign/archive/master.zip)

### Build
No build necessary. Downloaded zip file includes all necessary build gradle files for execution.

### Configuration Setup
Before running, need to create run/debug configuration.
1. To open the Run/Debug Configurations dialog, select **Run** and click **Edit Configurations**
2. Click **Add New Configuration**
3. Select a default template
4. Type a name in the **Name** field
5. Select from **Installation Option** dropdown to **Default APK**
6. Select from **Launch Option** dropdown to **Default Activity**
7. Click **Finish** to create.


### Device Installation
Application can be run either on a real Android device or on a Android Virtual device emulator. All functionality works on either device option.

### Set Up Android Virtual Device
If planning to run application through Android Studio, then an Android Virtual Device must be set up. These steps describe how to configure an Android Virtual Device that replicates a Pixel 2 XL.
1. In Android Studio, click the **Tools** menu, select **Android**, and click **AVD Manager** to open the Android Virtual Device Manager.
2. Use the Android Virtual Device Manager to create the Pixel 2 XL device. Click **Create Virtual Device**.
3. In the category list select **Phone**, then select the **Pixel 2 XL** device and click **Next**.
4. Select **Q** and click **Next**.
5. Ensure the Orientation is set to **Landscape** and click **Finish**.
6. In the Android Virtual Device Manager, start the Pixel 2 XL Android Virtual Device.

### Run Instructions
To run the application, follow these steps:
1. In toolbar, select the run/debug configuration from the **Select Run/Debug Configuration** drop-down list
2. Select **Run** and click **Run**
3. If using an AVM, the virtual device will be shown. The application will automatically be started. If not, search for the application on the device and manually start.
4. Application is ready to run any feature.

### Troubleshooting
Troubleshooting can be managed by using tools provided by the Android Studio environment. To debug the application, follow these steps:
1. In toolbar, select the run/debug configuration from the **Select Run/Debug Configuration** drop-down list
2. Select **Run** and click **Debug**
3. Debugger window should open. If not, select **View**, select **Tool Windows** and click **Debug**.

If experiencing any difficulties when running, the following are possible solutions:
* Make sure to set the API level to 23 or higher
* Create and select a run/debug configuration
* Install all listed prerequisites

For more information, visit [https://developer.android.com/studio/troubleshoot](https://developer.android.com/studio/troubleshoot)

