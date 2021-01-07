package gin.garin.githubuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DetailFragment : Fragment() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var detailViewModel: DetailViewModel

    companion object {

        private const val ARG_DATA = "arguments_data"

        @JvmStatic
        fun newInstance(url: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DATA, url)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(ARG_DATA) as String

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        val rvUser: RecyclerView = view.findViewById(R.id.fragment_rv_user)

        rvUser.layoutManager = LinearLayoutManager(activity)
        rvUser.adapter = adapter

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.setFollow(url)

        detailViewModel.getFollow().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
            }
        })

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val detail = Intent (activity, DetailActivity::class.java)
                detail.putExtra(DetailActivity.EXTRA_DATA, data.username)
                startActivity(detail)
            }
        })
    }
}