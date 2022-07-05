package com.neugelb.moviedirectory.ui

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.neugelb.moviedirectory.R
import com.neugelb.moviedirectory.databinding.FragmentSplashBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationView.addAnimatorListener(object :
            Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                navigateToDiscover()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

    private fun navigateToDiscover() {
        val action =
            SplashFragmentDirections.actionSplashToDiscover()
        view?.findNavController()?.navigate(action)
    }
}