# Linear Timer

Linear Timer is a custom view for Android that enables circular progress animation with respect to given duration.

Linear Timer supports following features -

1. Progress animation in clock-wise or counter clock-wise direction.
2. Get time elapsed since timer started or time left for the counter to complete.
3. Provide Start and finish points for the animation.
4. Pre-fill the progress up-till certain point.
5. Resume the animation on the basis of duration elapsed from total duration.

...and much more.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Linear%20Timer-brightgreen.svg?style=social)](https://android-arsenal.com/details/1/4959)

**If you're using this library, please let me know; I'll feature your app in this readme.**

## Screenshots

<img src="https://raw.githubusercontent.com/krtkush/LinearTimer/master/Screenshots/demo.gif" width="200" height="350" />

## Versioning

Linear Timer follows the [Semantic Versioning System](http://semver.org/).
It is currently on version `v2.0.1`.

## Setup

[![Release](https://jitpack.io/v/krtkush/LinearTimer.svg)]
(https://jitpack.io/#krtkush/LinearTimer)

Setup is pretty straight forward. 
In your project's `build.gradle` add the following - 

    allprojects {
      repositories {
          jcenter()
          maven { url "https://jitpack.io" }
      }
    }
    
And, in your app's `build.gradle` add this under `dependencies` block -

    compile 'com.github.krtkush:LinearTimer:v2.0.1'

## Usage

Following is how you can implement LinearTimer in its simplest form.

First, you need to add `LinearTimerView` into your XML layout -

    xmlns:app="http://schemas.android.com/apk/res-auto"

    <io.github.krtkush.lineartimer.LinearTimerView
        android:id="@+id/linearTimer"
        android:layout_centerHorizontal="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:radius="20dp"
        app:strokeWidth="3dp"/>
        
Note that `"wrap_content"` for `height` and `width` is the correct argument. Using other values might not lead incorrect rendering of the view on different devices.

After adding the view, here is how the View is initialized and used -

     LinearTimerView linearTimerView = (LinearTimerView) findViewById(R.id.linearTimer);

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

For detailed documentation and on how to customise and use LinearTimer [see this blog post](https://krtkush.github.io/2017/02/03/Linear-timer.html).

## Contribution

Any kind of contribution will be appreciated; feel free to create a pull request or file issues on the issue tracker.
