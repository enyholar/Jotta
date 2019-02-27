/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gideondev.jotta.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * A value holder that automatically clears the reference if the Fragment's view is destroyed.
 */
public class AutoClearedValue<T> {

  private T value;

  public AutoClearedValue(final Fragment fragment, T value) {
    final FragmentManager fragmentManager = fragment.getFragmentManager();
    fragmentManager.registerFragmentLifecycleCallbacks(
        new FragmentManager.FragmentLifecycleCallbacks() {
          @Override
          public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
            if (f == fragment) {
              AutoClearedValue.this.value = null;
              fragmentManager.unregisterFragmentLifecycleCallbacks(this);
            }
          }
        }, false);
    this.value = value;
  }

  public T get() {
    return value;
  }
}
