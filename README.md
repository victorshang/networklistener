# networklistener
 App Inventor 2 Extension Component NetworkListener v.1.0.1
 
I. Introduction：
  The APP Inventor 2 extension component detects the current mobile phone network state and provides event handlers when the mobile network status changes, and returns the changed status.
II.Usage

NetworkStatus(): returns the current state of the mobile network. The return value (int) means the following:
  -1: error
   0: no network
   1: Wifi connection
   2: Mobile connection
   other: other connections

NetworkChanged event: when the network changes, it occurs and returns the current network status. The return value (int) means the following:
  0: no network
  1: Wifi connection
  2: Mobile connection
  other: other connections
