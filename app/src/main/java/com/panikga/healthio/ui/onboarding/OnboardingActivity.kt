package com.panikga.healthio.ui.onboarding

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import com.panikga.healthio.R
import com.panikga.healthio.ui.authentication.login.LoginActivity

class OnboardingActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        supportActionBar?.hide()
        addSlide(
            AppIntroFragment.newInstance(
                title = "Welcome to Healthio!",
                description = "Taking care of your health has become\n" +
                        "easier! Let's learn more about it!",
                imageDrawable = R.drawable.ic_onboarding_1,
                titleColor = Color.parseColor("#4D9AF5"),
                descriptionColor = Color.parseColor("#333333"),
                backgroundColor = Color.parseColor("#EFF4FE")
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = "Get Rid of Queues",
                description = "Select a required specialist and make\n" +
                        "an appointment through the application.",
                imageDrawable = R.drawable.ic_onboarding_2,
                titleColor = Color.parseColor("#4D9AF5"),
                descriptionColor = Color.parseColor("#333333"),
                backgroundColor = Color.parseColor("#EFF4FE")
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = "Need any Emergencies?",
                description = "We provide you the nearest hospital\n" +
                        "based on your location.",
                imageDrawable = R.drawable.ic_group_2591,
                titleColor = Color.parseColor("#4D9AF5"),
                descriptionColor = Color.parseColor("#333333"),
                backgroundColor = Color.parseColor("#EFF4FE")
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
