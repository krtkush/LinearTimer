# Linear Timer

Linear Timer is a custom view for Android that enables circular progress animation with respect to given duration.

**Screenshots**

<img src="https://raw.githubusercontent.com/krtkush/LinearTimerProject/master/Screenshots/ssOne.png" width="200" height="350" />

**Setup**

Setup is pretty straight forward. 
In your root 'build.gradle' add the following - 

    allprojects {
      repositories {
          jcenter()
          maven { url "https://jitpack.io" }
      }
    }
    
 And, in your app 'build.gradle' add this - 
 
    `compile 'com.github.krtkush:LinearTimer:v1.0.0'`
under `dependencies`.

**Usage**

First, you need the following view in your layout XML - 

    ```
    xmlns:timer="http://schemas.android.com/apk/res-auto"
      
    <io.github.krtkush.lineartimer.LinearTimerView
      android:id="@+id/linearTimer"
      android:layout_centerInParent="true"
      android:layout_width="120dp"
      android:layout_height="120dp"
      timer:radius="20dp"
      timer:strokeWidth="3dp"
      timer:startingPoint="270"
      timer:preFillPoint="0" />
    ```
        
Here is a list of attributes available to toggle the LinearTimer's looks -

1. radius - The radius of the circle.
2. strokeWidth - the thickness of the circle boundary.
3. startingPoint - The angle from where, in the timer, you want the animation to start. 270 is the 12 O'Clock position.
4. preFillPoint - The angle up-till which you want the circle to be pre-filled.
5. initialColor - The initial color of the circle. 
6. progressColor - The color of the prgress arc that animates over the initial color. 

After adding the view, here is how the View is initiaized and used -

     LinearTimerView linearTimerView = (LinearTimerView) findViewById(R.id.linearTimer);
     LinearTimer linearTimer = new LinearTimer(linearTimerView);
     
     linearTimer.startTimer(360, 60 * 1000);
