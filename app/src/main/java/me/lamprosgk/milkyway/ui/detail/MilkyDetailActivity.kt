package me.lamprosgk.milkyway.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_milky_detail.*
import kotlinx.android.synthetic.main.content_milky_detail.*
import me.lamprosgk.milkyway.R
import me.lamprosgk.milkyway.domain.model.Milky


class MilkyDetailActivity : AppCompatActivity() {

    private lateinit var milky: Milky

    companion object {

        const val EXTRA_MILKY = "me.lamprosgk.milkyway.DetailActivity.EXTRA_MILKY"
        const val EXTRA_IMAGE_TRANSITION_NAME = "me.lamprosgk.milkyway.DetailActivity.EXTRA__IMAGE_TRANSITION_NAME"

        fun navigate(context: Context, milky: Milky, transition: String, bundle: Bundle?) {
            val intent = Intent(context, MilkyDetailActivity::class.java)
                .putExtra(EXTRA_MILKY, milky)
                .putExtra(EXTRA_IMAGE_TRANSITION_NAME, transition)
            context.startActivity(intent, bundle)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_milky_detail)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        with(intent ?: return) {
            milky = getParcelableExtra(EXTRA_MILKY) as Milky
            val transitionName = getStringExtra(EXTRA_IMAGE_TRANSITION_NAME)
            milkyImageDetail.transitionName = transitionName
        }

        setUpViews()
        loadMilkyImage()
    }

    private fun setUpViews() {
        with(milky) {
            milkyDetailTitle.text = title
            milkyDetailCenter.text = center
            milkyDetailDate.text = dateCreated
            milkyDetailDescription.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    private fun loadMilkyImage() {
        Picasso.get()
            .load(milky.imageUrl)
            .fit()
            .centerCrop()
            .into(milkyImageDetail, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }
            })
    }
}
