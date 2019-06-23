package me.lamprosgk.milkyway

import com.google.gson.Gson
import io.reactivex.Single
import me.lamprosgk.milkyway.data.mapper.SearchResultMapper
import me.lamprosgk.milkyway.data.remote.model.SearchResponse
import me.lamprosgk.milkyway.domain.model.Milky
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

object TestDataProvider {

    // return static json from resources
    private val json = readAsString("search_response.json")

    val successResponse: SearchResponse = Gson().fromJson(json, SearchResponse::class.java)

    val milkies =  SearchResultMapper().mapTo(successResponse.collection.items)

    private fun readAsString(path: String): String {
        return this.javaClass.classLoader?.getResourceAsStream(path)?.bufferedReader().use { it!!.readText() }
    }

    val successObservable = Single.just(milkies)

    val errorObservable: Single<List<Milky>> =
        Single.error(
            HttpException(
                Response.error<List<Milky>>(
                    500,
                    ResponseBody.create(null, "Internal Server Error")
                )
            )
        )

    val networkErrorObservable: Single<List<Milky>> =
        Single.error(UnknownHostException("Host unknown"))
}