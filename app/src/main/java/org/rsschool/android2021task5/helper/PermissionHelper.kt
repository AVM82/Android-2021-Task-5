package org.rsschool.android2021task5.helper

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import org.rsschool.android2021task5.ui.detail.DetailFragment

class PermissionHelper {

    fun permissionRequest(fragment : Fragment, callback: () -> Unit): ActivityResultLauncher<String> {

        return  fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                    callback.invoke()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }


    }
}
