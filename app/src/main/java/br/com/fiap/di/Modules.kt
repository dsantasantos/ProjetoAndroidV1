import androidx.room.Room
import br.com.fiap.MainViewModel
import br.com.fiap.WordListAdapter
import br.com.fiap.dao.WordRoomDatabase
import br.com.fiap.repository.WordRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        WordRepository(get())
    }
}
val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            WordRoomDatabase::class.java,
            "Word_database"
        ).build()
    }

    single {
        get<WordRoomDatabase>().wordDao()
    }
}
val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}

val uiModule = module {
    factory { WordListAdapter(get()) }
}
