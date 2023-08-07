package com.subbu.invoice.di

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.subbu.invoice.data.database.AppDatabase
import com.subbu.invoice.data.repository.CustomerRepoImp
import com.subbu.invoice.data.repository.InvoiceRepo
import com.subbu.invoice.data.repository.ItemRepo
import com.subbu.invoice.data.repository.VoucherRepo
import com.subbu.invoice.domain.repo.CustomerRepo
import com.subbu.invoice.presentaion.Form.Invoice.AddEntry
import com.subbu.invoice.presentaion.Form.Invoice.DeleteEntry
import com.subbu.invoice.presentaion.Form.Invoice.GetCustomerByTxt
import com.subbu.invoice.presentaion.Form.Invoice.GetEntries
import com.subbu.invoice.presentaion.Form.Invoice.GetInvoice
import com.subbu.invoice.presentaion.Form.Invoice.NewInvoiceUseCase
import com.subbu.invoice.presentaion.Form.Invoice.NewInvoiceVM
import com.subbu.invoice.presentaion.Form.Invoice.UpdateEntry
import com.subbu.invoice.presentaion.Form.Invoice.UpdateInvoice
import com.subbu.invoice.presentaion.Form.Voucher.VoucherFormVM
import com.subbu.invoice.presentaion.Invoice.GetAllInvoice
import com.subbu.invoice.presentaion.Invoice.InvoiceUseCaes
import com.subbu.invoice.presentaion.Invoice.InvoiceViewModel
import com.subbu.invoice.presentaion.Voucher.GetAllVouchers
import com.subbu.invoice.presentaion.Voucher.GetVoucher
import com.subbu.invoice.presentaion.Voucher.UpdateVouchers
import com.subbu.invoice.presentaion.Voucher.VoucherUseCase
import com.subbu.invoice.presentaion.Voucher.VoucherVM
import com.subbu.invoice.presentaion.home.HomeViewModel
import com.subbu.invoice.presentaion.items.ItemsListingVM
import com.subbu.invoice.presentaion.setting.GetAllCustomers
import com.subbu.invoice.presentaion.setting.GetAllItems
import com.subbu.invoice.presentaion.setting.SettingUseCase
import com.subbu.invoice.presentaion.setting.SettingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dbName: String = "db_8";

val appModule = module {
    single {
        Log.i("ROOM", "On INIT")
        Room.databaseBuilder(
            androidApplication(), AppDatabase::class.java, "${dbName}.db"
        ).allowMainThreadQueries()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Log.i("ROOM", "On Create")
                }
            }).build();

    }
    single { get<AppDatabase>().invoice() }
    single { get<AppDatabase>().customers() }
    single { get<AppDatabase>().items() }
    single { get<AppDatabase>().vouchers() }
    single { getSharedPrefs(androidApplication()) }

// Repos
    single<CustomerRepo> { CustomerRepoImp(get(), get()) }
    single<ItemRepo> { ItemRepo(get(), get()) }
    single { InvoiceRepo(get(), get()) }
    single { VoucherRepo(get(), get()) }

//   Usecases
    factory { GetAllCustomers(get()) }
    factory { GetAllItems(get()) }
    factory { GetAllInvoice(get()) }
    factory { GetCustomerByTxt(get()) }
    factory { AddEntry(get()) }
    factory { GetInvoice(get()) }
    factory { UpdateInvoice(get()) }
    factory { GetEntries(get()) }
    factory { UpdateEntry(get()) }
    factory { DeleteEntry(get()) }
    factory { GetAllVouchers(get()) }
    factory { UpdateVouchers(get()) }
    factory { GetVoucher(get()) }


    single {
        SettingUseCase(
            getCustomers = get(),
            getItems = get(),
        )
    }
    single { InvoiceUseCaes(getInvoices = get()) }
    single { NewInvoiceUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
    single { VoucherUseCase(get(), get(), get(), get()) }


// ViewModels
    viewModel { HomeViewModel(get<CustomerRepo>()) }
    viewModel { SettingViewModel(get()) }
    viewModel { InvoiceViewModel(get()) }
    viewModel { NewInvoiceVM(get()) }
    viewModel { ItemsListingVM(get()) }
    viewModel { VoucherVM(get()) }
    viewModel { VoucherFormVM(get()) }
}


fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(dbName, android.content.Context.MODE_PRIVATE)
}

