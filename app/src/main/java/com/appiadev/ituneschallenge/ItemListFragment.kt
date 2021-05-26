package com.appiadev.ituneschallenge

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appiadev.ituneschallenge.data.api.ApiHelper
import com.appiadev.ituneschallenge.data.api.ApiService
import com.appiadev.ituneschallenge.data.api.RetrofitBuilder
import com.appiadev.ituneschallenge.data.model.iTunesResponse
import com.appiadev.ituneschallenge.placeholder.PlaceholderContent;
import com.appiadev.ituneschallenge.databinding.FragmentItemListBinding
import com.appiadev.ituneschallenge.ui.base.ViewModelFactory
import com.appiadev.ituneschallenge.ui.main.adapter.MainAdapter
import com.appiadev.ituneschallenge.ui.main.viewmodel.MainViewModel
import com.appiadev.ituneschallenge.utils.Status

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class ItemListFragment : Fragment() {

    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    private val unhandledKeyEventListenerCompat =
        ViewCompat.OnUnhandledKeyEventListenerCompat { v, event ->
            if (event.keyCode == KeyEvent.KEYCODE_Z && event.isCtrlPressed) {
                Toast.makeText(
                    v.context,
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
                ).show()
                true
            } else if (event.keyCode == KeyEvent.KEYCODE_F && event.isCtrlPressed) {
                Toast.makeText(
                    v.context,
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
                ).show()
                true
            }
            false
        }

    private var _binding: FragmentItemListBinding? = null

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    var onClickListener : View.OnClickListener? = null
    var onContextClickListener : View.OnContextClickListener? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat)

        val recyclerView: RecyclerView = binding.itemList
        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        onClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag as PlaceholderContent.PlaceholderItem
            val bundle = Bundle()
            bundle.putString(
                ItemDetailFragment.ARG_ITEM_ID,
                item.id
            )
            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_item_detail, bundle)
            } else {
                itemView.findNavController().navigate(R.id.show_item_detail, bundle)
            }
        }

        /**
         * Context click listener to handle Right click events
         * from mice and trackpad input to provide a more native
         * experience on larger screen devices
         */
        onContextClickListener = View.OnContextClickListener { v ->
            val item = v.tag as PlaceholderContent.PlaceholderItem
            Toast.makeText(
                v.context,
                "Context click of item " + item.id,
                Toast.LENGTH_LONG
            ).show()
            true
        }

        setupViewModel()
        setupObservers(recyclerView)
    }

    private fun setupViewModel() {
        val apiService: ApiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupObservers(recyclerView: RecyclerView) {
        viewModel.getSongs().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        //progressBar.visibility = View.GONE
                        resource.data?.let { response -> retrieveList(response,recyclerView) }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        //progressBar.visibility = View.GONE
                        //Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        //progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(response: iTunesResponse, recyclerView: RecyclerView) {
        adapter = MainAdapter(arrayListOf(),onClickListener!!,onContextClickListener!!)

        adapter.apply {
            addUsers(response.results)
            notifyDataSetChanged()
        }

        setupRecyclerView(recyclerView, adapter)
    }
    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: MainAdapter,
    ) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}