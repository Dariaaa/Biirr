package ru.dariaaa.biirr.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import ru.dariaaa.biirr.MainActivity
import ru.dariaaa.biirr.R
import ru.dariaaa.biirr.data.cache.model.BeerModel
import ru.dariaaa.biirr.databinding.FragmentBeerDetailsBinding
import ru.dariaaa.biirr.presentation.AbstractFragment
import ru.dariaaa.biirr.presentation.handlers.ImageLoader
import javax.inject.Inject
import javax.inject.Named

class BeerDetailsFragment : AbstractFragment<BeerDetailsFragment.BeerListHolder>() {
    private val args: BeerDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentBeerDetailsBinding

    @Inject
    lateinit var viewModel: BeerDetailsViewModel

    @Inject
    @Named("Glide")
    lateinit var imageLoader: ImageLoader

    override fun createHolder(view: View): BeerListHolder = BeerListHolder(binding, imageLoader)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeerDetailsBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).appComponent.inject(this)

        holder = createHolder(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state().observe(viewLifecycleOwner, requireHolder()::bind)
        requireHolder().setId(args.id.toInt())

    }

    override fun onDetach() {
        super.onDetach()
        viewModel.destroy()
    }

    inner class BeerListHolder(
        val binding: FragmentBeerDetailsBinding,
        val imageLoader: ImageLoader
    ) : AbstractFragment.Holder() {


        fun bind(beer: BeerModel) {
            beer.picUrl?.apply {
                imageLoader.loadDrawable(binding.image, this)
            }
            binding.tagline.text = beer.tagline
            binding.name.text = beer.name

            beer.abv?.also {
                binding.abv.text = "$it%"
            }

            beer.ibu?.also {
                binding.ibu.text = getIbuText(it)
            }
            beer.foodPairing?.also {
                binding.foodPairingLabel.text = it
            }

        }
        private fun getIbuText(it:Double): String {
            val ibuTextSrc = when {
                it <= 20 -> {
                    R.string.smooth
                }
                it <= 50 -> {
                    R.string.bitter
                }
                else -> R.string.hipster_plus
            }
            return requireContext().getString(ibuTextSrc)
        }

        fun setId(id: Int) {
            viewModel.load(id)
        }
    }

}