package me.lamprosgk.milkyway.data.repository

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import me.lamprosgk.milkyway.TestDataProvider.successResponse
import me.lamprosgk.milkyway.data.mapper.SearchResultMapper
import me.lamprosgk.milkyway.data.remote.api.NasaService
import me.lamprosgk.milkyway.data.remote.model.SearchResponse
import me.lamprosgk.milkyway.domain.model.Milky
import me.lamprosgk.milkyway.domain.repository.MilkiesRepository
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import org.mockito.Mockito.`when` as whenever


class MilkiesRepositoryImplTest {

    @Mock
    lateinit var nasaService: NasaService
    private lateinit var repository: MilkiesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = MilkiesRepositoryImpl(nasaService, SearchResultMapper())
    }

    @Test
    fun getImagesSuccess_InvokesCorrectMethods() {

        whenever(nasaService.getImages(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Single.just(successResponse))

        val observer = TestObserver<List<Milky>>()
        repository.getImages("lampros", "image", "2018", "2019").subscribe(observer)

        observer.awaitTerminalEvent()
        observer.assertNoErrors()

        verify(nasaService).getImages("lampros", "image", "2018", "2019")
    }

    @Test
    fun getImagesSuccess_ReturnsCorrectResults() {

        whenever(nasaService.getImages(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Single.just(successResponse))

        val observer = TestObserver<List<Milky>>()
        repository.getImages("lampros", "image", "2018", "2019").subscribe(observer)

        observer.awaitTerminalEvent()
        observer.assertNoErrors()

        // verify emitted result
        val onNextEmission = observer.values().first()

        // SearchResultMapper functionality is tested essentially as well with below, so separate dedicated tests are not needed

        with(onNextEmission.first()) {
            assertEquals("GSFC", center)
            assertEquals("2017-12-08", dateCreated)
            assertEquals(
                "This image shows the star-studded center of the Milky Way towards the constellation " +
                        "of Sagittarius. The crowded center of our galaxy contains numerous complex and mysterious objects " +
                        "that are usually hidden at optical wavelengths by clouds of dust — but many are visible here in " +
                        "these infrared observations from Hubble.  However, the most famous cosmic object in this image " +
                        "still remains invisible: the monster at our galaxy’s heart called Sagittarius A*. Astronomers " +
                        "have observed stars spinning around this supermassive black hole (located right in the center of " +
                        "the image), and the black hole consuming clouds of dust as it affects its environment with its " +
                        "enormous gravitational pull.  Infrared observations can pierce through thick obscuring material " +
                        "to reveal information that is usually hidden to the optical observer. This is the best infrared " +
                        "image of this region ever taken with Hubble, and uses infrared archive data from Hubble’s Wide " +
                        "Field Camera 3, taken in September 2011.     Credit: NASA, ESA, and G. Brammer " +
                        "<b><a href=\"http://www.nasa.gov/audience/formedia/features/MP_Photo_Guidelines.html\" " +
                        "rel=\"nofollow\">NASA image use policy.</a></b>  <b><a href=\"http://www.nasa.gov/centers/goddard/home/index.html\" " +
                        "rel=\"nofollow\">NASA Goddard Space Flight Center</a></b> enables NASA’s mission through four " +
                        "scientific endeavors: Earth Science, Heliophysics, Solar System Exploration, and Astrophysics. " +
                        "Goddard plays a leading role in NASA’s accomplishments by contributing compelling scientific " +
                        "knowledge to advance the Agency’s mission.  <b>Follow us on <a href=\"http://twitter.com/NASA_GoddardPix\" " +
                        "rel=\"nofollow\">Twitter</a></b>  <b>Like us on " +
                        "<a href=\"http://www.facebook.com/pages/Greenbelt-MD/NASA-Goddard/395013845897?ref=tsd\" " +
                        "rel=\"nofollow\">Facebook</a></b>  <b>Find us on <a href=\"http://instagram.com/nasagoddard?vm=grid\" " +
                        "rel=\"nofollow\">Instagram</a></b>", description
            )
            assertEquals(
                "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001362/GSFC_20171208_Archive_e001362~thumb.jpg",
                imageUrl
            )
            assertEquals("A monster in the Milky Way", title)
            assertEquals("GSFC_20171208_Archive_e001362", nasaId)
        }
    }

    @Test
    fun getImagesFailure_TerminatesWithError() {

        val errorObservable: Single<SearchResponse> =
            Single.error(
                HttpException(
                    Response.error<List<Milky>>(
                        500,
                        ResponseBody.create(null, "Internal Server Error")
                    )
                )
            )

        whenever(nasaService.getImages(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(errorObservable)

        val observer = TestObserver<List<Milky>>()
        repository.getImages("lampros", "image", "2018", "2019").subscribe(observer)

        observer.awaitTerminalEvent()
        observer.assertError(HttpException::class.java)

        verify(nasaService).getImages("lampros", "image", "2018", "2019")
    }
}