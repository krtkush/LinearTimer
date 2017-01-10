# Linear Timer

Linear Timer is a custom view for Android that enables circular progress animation with respect to given duration.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Linear%20Timer-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/4959)

**If you're using this library, please let me know; I'll feature your app in this readme.**

## Screenshots

<img src="https://raw.githubusercontent.com/krtkush/LinearTimerProject/master/Screenshots/ssOne.png" width="200" height="350" /> <img src="https://raw.githubusercontent.com/krtkush/LinearTimer/master/Screenshots/intro.gif" width="200" height="350" />

## Setup

Setup is pretty straight forward. 
In your root 'build.gradle' add the following - 

    allprojects {
      repositories {
          jcenter()
          maven { url "https://jitpack.io" }
      }
    }
    
And, in your app 'build.gradle' add this - 

     compile 'com.github.krtkush:LinearTimer:v1.0.0'
under `dependencies`.

## Usage

First, you need the following view in your layout XML - 

    xmlns:app="http://schemas.android.com/apk/res-auto"
      
    <io.github.krtkush.lineartimer.LinearTimerView
      android:id="@+id/linearTimer"
      android:layout_centerInParent="true"
      android:layout_width="120dp"
      android:layout_height="120dp"
      app:radius="20dp"
      app:strokeWidth="3dp"
      app:startingPoint="270"
      app:preFillPoint="0" />
        
Here is a list of attributes available to toggle the LinearTimer's basic style -

1. **radius** - The radius of the circle.
2. **strokeWidth** - the thickness of the circle boundary.
3. **startingPoint** - The angle from where, in the timer, you want the animation to start. 270 is the 12 O'Clock position.
4. **preFillPoint** - The angle up-till which you want the circle to be pre-filled.
5. **initialColor** - The initial color of the circle. 
6. **progressColor** - The color of the prgress arc that animates over the initial color. 

After adding the view, here is how the View is initiaized and used -

     LinearTimerView linearTimerView = (LinearTimerView) findViewById(R.id.linearTimer);
     LinearTimer linearTimer = new LinearTimer(linearTimerView);
     
     /** 
      * Start the timer. 
      *
      * First argument represents the angle at which the animation should finish.
      * Second argument represents the duration of the animation in milliseconds. 
      */
     linearTimer.startTimer(360, 60 * 1000);
     
     /**
      * Reset the timer to the start angle and then start the progress again.
      */
     linearTimer.restartTimer();
     
      /**
      * Method to reset the timer to start angle.
      */
     linearTimer.resetTimer();
     
## Contribution

Any kind of contribution will be appreciated; feel free to create a pull request or file issues on the issue tracker. If you'd like to contact me, you can reach me at kartikey92[at]gmail.com.
