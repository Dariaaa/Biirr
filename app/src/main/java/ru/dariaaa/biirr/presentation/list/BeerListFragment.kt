package ru.dariaaa.biirr.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.dariaaa.biirr.MainActivity
import ru.dariaaa.biirr.databinding.FragmentBeerListBinding
import ru.dariaaa.biirr.presentation.AbstractFragment
import ru.dariaaa.biirr.presentation.handlers.ImageLoader
import javax.inject.Inject
import javax.inject.Named

@ExperimentalPagingApi
class BeerListFragment : AbstractFragment<BeerListFragment.BeerDetailsHolder>(), BeerListAdapter.IListener {

    @Inject
    lateinit var viewModel: BeerListViewModel

    @Inject
    @Named("Glide")
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var adapter: BeerListAdapter

    lateinit var binding: FragmentBeerListBinding

    override fun createHolder(view: View): BeerDetailsHolder = BeerDetailsHolder(binding, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeerListBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).appComponent.inject(this)

        holder = createHolder(binding.root)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireHolder().bind()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onClick(id: Int) {
        findNavController().navigate(
            BeerListFragmentDirections.actionBeerListFragmentToBeerDetailsFragment(id.toString())
        )
    }


    inner class BeerDetailsHolder(
        private val binding: FragmentBeerListBinding,
        private val listener: BeerListAdapter.IListener
    ) : AbstractFragment.Holder() {

        fun bind() {
            adapter.onClickListener = listener
            binding.list.adapter = adapter
            binding.list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            viewModel.source().observe(viewLifecycleOwner) {
                viewLifecycleOwner.lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }

        }
    }

}