package org.alphasupercoolwolfsquad

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback
import java.io.File

fun main(args:Array<String>) {
    val petServiceApi by lazy { PetServiceAPI.create() }

    val requestMap = HashMap<String, RequestBody>()
    requestMap.put("name", createPartFromString("Test"))
    requestMap.put("filename", createPartFromString("Test.jpg"))
    requestMap.put("type", createPartFromString("image/jpg"))

    val requestFile = createFilePart("data")

    var disposable: Disposable? = null
    disposable = petServiceApi.postRequestTwo(requestMap, requestFile)
            .subscribeOn(Schedulers.io())
            .subscribe(
                    { result ->
                        println("Result" + result.toString() + " " + result.id + " " + result.label + " " + result.score)
                    },
                    { error -> 
                        println("Error" + error.toString())
                    }
            )
}

fun createPartFromString(descriptor: String) = RequestBody.create(MultipartBody.FORM, descriptor)
fun createFilePart(descriptor: String): MultipartBody.Part {
    val file = File("test.jpg")
    val requestFile = RequestBody.create(
            MultipartBody.FORM,
            file
    )
    return MultipartBody.Part.createFormData(descriptor, file.name, requestFile)
}