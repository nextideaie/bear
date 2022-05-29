# Animation Login page

[![](https://jitpack.io/v/llcnextidea/bear.svg)](https://jitpack.io/#llcnextidea/bear)
[![API](https://img.shields.io/badge/API-21%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=21)

Login Form is one of the most common screens you can find in any app.

A simple library to make you login page more interesting.
Animation bear view
## Setup project

Add following line of code in your root build.gradle

```kotlin
allprojects {
   repositories {
      maven { url 'https://jitpack.io' }
   }
}
```

#### Gradle:

Add following line of code to your module (app) level gradle file:

```groovy
    implementation 'am.nextidea:bear:<LATEST-VERSION>'
```

#### Kotlin:

```kotlin
    implementation 'am.nextidea:bear:<LATEST-VERSION>'
```

## Setup and usage:
Add BearView in your layout

```xml
   <am.nextidea.bear.BearView
        android:id="@+id/customBearView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

   <androidx.appcompat.widget.AppCompatEditText
        android:id="@+id/login"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Email"
        android:layout_marginHorizontal="80dp"
        android:layout_marginTop="10dp"
        android:inputType="textEmailAddress"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/customBearView" />

```
Now set up bear view
```kotlin
    binding.customBearView.setupView(login)
```
## What are you showing
<img src="[https://github.com/llcnextidea/bear/tree/main/video/record_2.gif](https://github.com/llcnextidea/bear/blob/main/video/record.gif)" />

Now we are making more interesting:

Add password EditText:

In the below login add this 
```xml
    <androidx.appcompat.widget.LinearLayoutCompat
        android:id="@+id/pass"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="80dp"
        android:layout_marginTop="10dp"
        android:orientation="horizontal"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/login">

        <androidx.appcompat.widget.AppCompatEditText
            android:id="@+id/password"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Password"
            android:paddingEnd="60dp"
            android:inputType="textPassword" />

        <androidx.appcompat.widget.AppCompatImageButton
            android:id="@+id/eyeButton"
            style="?android:buttonBarButtonStyle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="-60dp"
            android:src="@drawable/ic_eye_hidden" />

    </androidx.appcompat.widget.LinearLayoutCompat>
```

Change set up bear view
```kotlin
     with(binding) {
            customBearView.setupView(login,password)
            eyeButton.setOnClickListener {
                customBearView.onViewPassword { isVisible ->
                    if (isVisible) {
                        eyeButton.setImageDrawable(
                            ContextCompat.getDrawable(
                                it.context,
                                R.drawable.ic_eye_show
                            )
                        )
                    } else {
                        eyeButton.setImageDrawable(
                            ContextCompat.getDrawable(
                                it.context,
                                R.drawable.ic_eye_hidden
                            )
                        )
                    }
                }
                val cursorPosition = binding.password.text?.length ?: 0
                binding.password.setSelection(cursorPosition)
            }
        }
```

## How the app works
<img src="https://github.com/llcnextidea/bear/tree/main/video/record.gif" width="500" />

## Contact

- **Email**: llcnextidea@gmail.com
- **Website**: https://apk.am/
- **linkedin**: https://www.linkedin.com/in/karen-melkonyan-466166195/
- **Google Play**: https://play.google.com/store/apps/dev?id=6637853866787617624

## Licence

```
   Copyright (c) 2022 Karen Melkonyan

   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in all
   copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
```

