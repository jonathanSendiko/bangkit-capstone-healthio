package com.example.healthio.ui.onboarding

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.healthio.R
import com.example.healthio.ui.authentication.login.LoginActivity
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class OnboardingActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        supportActionBar?.hide()
        addSlide(
            AppIntroFragment.newInstance(
                title = "Welcome...",
                description = "This is the first slide of the example",
                imageDrawable = R.drawable.ic_person,
                titleColor = Color.YELLOW,
                descriptionColor = getColor(R.color.blueaccent),
                backgroundColor = Color.parseColor("#2EC4B6")
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = "...Let's get started!",
                description = "This is the last slide, I won't annoy you more :)",
                imageDrawable = R.drawable.ic_healthio,
                titleColor = Color.YELLOW,
                descriptionColor = getColor(R.color.blue),
                backgroundColor = Color.parseColor("#2EC4B6")
            )
        )

        setTransformer(AppIntroPageTransformerType.Fade)
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
