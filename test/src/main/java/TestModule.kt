import car.BMW
import car.Car
import com.re4rk.arkdi.Provides
import engine.Engine
import engine.LPG

object TestModule {
    @Provides
    fun provideEngine(): Engine {
        return LPG
    }

    @Provides
    fun provideCar(engine: Engine): Car {
        return BMW(engine)
    }
}
