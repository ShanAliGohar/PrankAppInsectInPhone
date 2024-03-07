package com.example.prankappinsectinphone.adapters.`interface`

interface OnBackPressedListener {
    /**
     * Called when the back button is pressed.
     * @return True if the back press event is consumed by the fragment, false otherwise.
     */
    fun onBackPressed(): Boolean
}