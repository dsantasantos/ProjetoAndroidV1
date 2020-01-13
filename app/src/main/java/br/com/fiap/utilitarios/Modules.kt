import androidx.room.Room
import br.com.fiap.dados.CnhRoomDatabase
import br.com.fiap.dados.repositorios.CnhRepository
import br.com.fiap.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        CnhRepository(get())
    }
}
val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CnhRoomDatabase::class.java,
            "Cnh_database"
        ).build()
    }

    single {
        get<CnhRoomDatabase>().cnhDao()
    }
}
val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}
