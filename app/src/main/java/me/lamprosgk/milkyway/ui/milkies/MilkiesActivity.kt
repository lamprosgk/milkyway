package me.lamprosgk.milkyway.ui.milkies

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_milkies.*
import me.lamprosgk.milkyway.MilkyWayApplication
import me.lamprosgk.milkyway.R
import me.lamprosgk.milkyway.domain.model.Milky
import me.lamprosgk.milkyway.ui.detail.MilkyDetailActivity
import me.lamprosgk.milkyway.util.gone
import me.lamprosgk.milkyway.util.visible
import javax.inject.Inject

class MilkiesActivity : AppCompatActivity(), MilkiesContract.View {

    @Inject
    override lateinit var mPresenter: MilkiesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_milkies)
        (application as MilkyWayApplication).appComponent.inject(this)
        mPresenter.setView(this)
        setupRecyclerView()
        swipeRefreshContainer.setOnRefreshListener { loadSearchResults() }
    }

    override fun onResume() {
        super.onResume()
        loadSearchResults()
    }

    private fun loadSearchResults() {
        mPresenter.getImages()
    }

    override fun showLoading() {
        swipeRefreshContainer.isRefreshing = true
        emptyTextView.text = getString(R.string.message_loading)
    }

    override fun hideLoading() {
        swipeRefreshContainer.isRefreshing = false
        emptyTextView.gone()
    }

    override fun showError(messageResId: Int) {
        milkiesRecyclerView.gone()
        emptyTextView.visible()
        emptyTextView.text = getString(messageResId)
    }

    override fun showImages(milkies: List<Milky>) {
        milkiesRecyclerView.visible()
        (milkiesRecyclerView.adapter as MilkiesAdapter).setItems(milkies)

    }

    private fun setupRecyclerView() {
        milkiesRecyclerView.layoutManager = LinearLayoutManager(this)
        milkiesRecyclerView.adapter = MilkiesAdapter(object : MilkiesAdapter.ItemClickListener {
            override fun onItemClick(milky: Milky, image: ImageView) {
                val transition = ViewCompat.getTransitionName(image)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MilkiesActivity, image,
                    transition!!
                )
                MilkyDetailActivity.navigate(this@MilkiesActivity, milky, transition, options.toBundle())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
