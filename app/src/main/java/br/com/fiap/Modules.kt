import androidx.room.Room
import br.com.fiap.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        TesteTabelaRepository(get())
    }
}
val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            WordRoomDatabase::class.java,
            "Word_database2"
        ).build()
    }

    single {
        get<WordRoomDatabase>().wordDao()
    }
}
val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}

/*
val uiModule = module {
    factory { WordListAdapter(get()) }
}
 */
