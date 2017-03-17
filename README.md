<img src="https://raw.githubusercontent.com/krtkush/LinearTimer/master/lineartimer/src/main/res/drawable/high_res_icon.png" height="200" width="200"/>

# Linear Timer

Linear Timer is a custom view for Android that enables circular progress animation with respect to given duration.

Linear Timer supports following features -

1. Progress animation in clock-wise or counter clock-wise direction.
2. Get time elapsed since timer started or time left for the counter to complete.
3. Provide Start and finish points for the animation.
4. Pre-fill the progress up-till certain point.
5. Resume the animation on the basis of duration elapsed from total duration.

...and much more.

Download the demo app -

<a href='https://play.google.com/store/apps/details?id=io.github.krtkush.lineartimeproject&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="160" height="65"></a>


[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Linear%20Timer-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/4959)

**If you're using this library, please let me know; I'll feature your app in the wiki.**

## Screenshots

<img src="https://raw.githubusercontent.com/krtkush/LinearTimer/master/Screenshots/demo.gif" width="200" height="350" />

## Versioning

Linear Timer follows the [Semantic Versioning System](http://semver.org/).

## Setup


[![Release](https://jitpack.io/v/krtkush/LinearTimer.svg)](https://jitpack.io/#krtkush/LinearTimer)

Setup is pretty straight forward. 
In your project's `build.gradle` add the following - 

    allprojects {
      repositories {
          jcenter()
          maven { url "https://jitpack.io" }
      }
    }
    
And, in your app's `build.gradle` add this under `dependencies` block -

    compile 'com.github.krtkush:LinearTimer:<version_available_on_jitpack>'
    
example - `compile 'com.github.krtkush:LinearTimer:v2.1.0'`

## Usage

First, you need to add `LinearTimerView` into your XML layout -

    xmlns:timer="http://schemas.android.com/apk/res-auto"

    <io.github.krtkush.lineartimer.LinearTimerView
        android:id="@+id/linearTimer"
        android:layout_centerHorizontal="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        timer:radius="20dp"
        timer:strokeWidth="3dp"/>

Note that `"wrap_content"` for height and width is recommended. Using other values might not lead correct rendering of the view.

After adding the view, here is how it is initialized and used -

    LinearTimerView linearTimerView = (LinearTimerView)
                                        findViewById(R.id.linearTimer);

    LinearTimer linearTimer = new LinearTimer.Builder()
                .linearTimerView(linearTimerView)
                .duration(10 * 1000)
                .build();

    // Start the timer.
    findViewById(R.id.startTimer).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          linearTimer.startTimer();
        }
    });

    // Restart the timer.
    findViewById(R.id.restartTimer).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            linearTimer.restartTimer();
          }
    });

    // Pause the timer
    findViewById(R.id.pauseTimer).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             try {
                linearTimer.pauseTimer();
             } catch (IllegalStateException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
             }
          }
     });

    // Resume the timer
    findViewById(R.id.resumeTimer).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
              try {
                linearTimer.resumeTimer();
              } catch (IllegalStateException e) {
                  e.printStackTrace();
                  Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }
          }
     });

    // Reset the timer
    findViewById(R.id.resetTimer).setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           try {
               linearTimer.resetTimer();
           } catch (IllegalStateException e) {
               e.printStackTrace();
               Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
           }
       }
    });
   
List of methods available to control the timer -


| | Method | Description | Exception |
|---|---|---|---|
|1.|[startTimer()](https://github.com/krtkush/LinearTimer/wiki/Usage#start)|Start the timer.|IllegalStateException|
|2.|[pauseTimer()](https://github.com/krtkush/LinearTimer/wiki/Usage#pause)|Pause the timer.|IllegalStateException|
|3.|[resumeTimer()](https://github.com/krtkush/LinearTimer/wiki/Usage#resume)|Resume the timer from its pause position.|IllegalStateException|
|4.|[resetTimer()](https://github.com/krtkush/LinearTimer/wiki/Usage#reset)|Reset the timer to the starting angle; the timer will not start after reset.|IllegalStateException|
|5.|[restartTimer()](https://github.com/krtkush/LinearTimer/wiki/Usage#restart)|Restart the timer from the starting angle; the timer will start again.|None|
|5.|[getState()](https://github.com/krtkush/LinearTimer/wiki/Usage#timer-states)|Get current state of the timer.|None|

For detailed documentation and on how to customise and use LinearTimer please [refer to the wiki](https://github.com/krtkush/LinearTimer/wiki).

## Contribution

Any kind of contribution will be appreciated; feel free to create a pull request or file issues on the issue tracker.

List of current contributors can be found [here](https://github.com/krtkush/LinearTimer/graphs/contributors).
