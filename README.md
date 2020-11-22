# Twist-On-Time

######################### Release Notes ATAK Stand-Alone 1.0 ###########################

############################### NEW FEATURES ####################################

- Developed stand-alone ATAK application
- Allowed users to create an alarm or countdown timer
- Allowed users to view a progress bar for the time remaining on a timer
- Allowed users to add early warning notifications for a timer
- Allowed users to view a list of existing timers 
- Allowed users to view a list of existing early warning notifications 

################################ BUG FIXES ######################################

- Developed an alarm list for persistence (saving state when exiting the application)
- Completed alarm manager conversion/integration 
- Attempted integration with original ATAP application repository (due to missing code needed to stop effort for now) 

################################ KNOWN BUGS ######################################

- Application appears to only run while the SDK (i.e. Android device) is sleeping, will not run when the device is turned off (i.e. rebooted)
- When a user edits the timer (not attempting to change the time or name), by adding an early warning notification, 
  when brought back to the view timers screen the alarm has reset to the original time (rather than running while editing, it will reset)
- Appears to be a 2 second delay between the early warrning noticication sound and the time left on timer (having notifications seconds apart will be an issue)
