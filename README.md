# JIC-134 Twist-On-Time

## ##############Release Notes ATAK Stand-Alone 1.0##############

## New Features
* Developed stand-alone ATAK application
* Allowed users to create an alarm or countdown timer
* Allowed users to view a progress bar for the time remaining on a timer
* Allowed users to add early warning notifications for a timer
* Allowed users to view a list of existing timers
* Allowed users to view a list of existing early warning notifications

## Bug Fixes
* Developed an alarm list for persistence (saving state when exiting the application)
* Completed alarm manager conversion/integration
* Attempted integration with original ATAK application repository (due to missing code needed to stop effort for now)

## Known Bugs
* Application appears to only run while the SDK (i.e. Android device) is sleeping, will not run when the device is turned off (i.e. rebooted)
* When a user edits the timer (not attempting to change the time or name), by adding an early warning notification,
  when brought back to the view timers screen the alarm has reset to the original time (rather than running while editing, it will reset)
* Appears to be a 2 second delay between the early warning notification sound and the time left on timer (having notifications seconds apart will be an issue)


## #####################Installation Guide#####################

## Prerequisites
* JDK 1.8
* Android SDK
    * Target SDK Version: 29
    * Minimum SDK Version: 23
* Generating Signed APK
    * From Android Studio:
        1. ***Build*** menu
        2. ***Generate Signed APK...***
        3. Fill in the keystore information (you only need to do this once manually and then let Android Studio remember it)

## Setting Up Environment
* Download and install Android Studio
    * [https://developer.android.com/studio](https://developer.android.com/studio)

## Dependencies
* Download Android version 10 or higher.
    * See [https://developer.android.com/studio/releases/platforms](https://developer.android.com/studio/releases/platforms)

## Set Up Android Virtual Device
If planning to run application through Android Studio, then an Android Virtual Device must be set up. These steps describe how to configure an Android Virtual Device that replicates a Pixel 2 XL.
1. In Android Studio, click the **Tools** menu, select **Android**, and click **AVD Manager** to open the Android Virtual Device Manager.
2. Use the Android Virtual Device Manager to create the Pixel 2 XL device. Click **Create Virtual Device**.
3. In the category list select **Phone**, then select the **Pixel 2 XL** device and click **Next**.
4. Select **Q** and click **Next**.
5. Ensure the Orientation is set to **Landscape** and click **Finish**.
6. In the Android Virtual Device Manager, start the Pixel 2 XL Android Virtual Device.

## Installation
Clone this repository and import into **Android Studio**

`git clone https://github.com/Nathanieljg/JuniorDesign.git`

OR

Download with [https://github.com/Nathanieljg/JuniorDesign/archive/master.zip](https://github.com/Nathanieljg/JuniorDesign/archive/master.zip)

