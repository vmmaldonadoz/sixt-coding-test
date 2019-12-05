package com.example.rental.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rental.R
import com.example.rental.data.room.Car
import com.example.rental.ui.viewmodels.SharedMainViewModel
import com.example.rental.ui.views.CarItem
import com.example.rental.ui.views.ItemTitle
import com.example.rental.utils.subscribeOnIO
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class CarListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: SharedMainViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders
            .of(requireActivity(), viewModelFactory)
            .get(SharedMainViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable.add(
            viewModel.getAvailableCars()
                .subscribeOnIO()
                .subscribeBy(
                    onNext = { cars -> showAvailableCars(cars) },
                    onError = { error -> error.printStackTrace() }
                )
        )
    }

    private fun showAvailableCars(cars: List<Car>) {
        recyclerView.withModels {

            ItemTitle(getString(R.string.available_cars))
                .id(1)
                .addTo(this)

            cars.forEach { car ->
                CarItem(car)
                    .id(car.id)
                    .addTo(this)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): CarListFragment {
            return CarListFragment()
        }
    }
}