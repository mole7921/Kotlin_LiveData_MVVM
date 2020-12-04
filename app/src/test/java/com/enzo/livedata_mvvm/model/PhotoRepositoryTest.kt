package com.enzo.livedata_mvvm.model

import com.enzo.livedata_mvvm.retrofit.ApiService
import com.enzo.livedata_mvvm.retrofit.Resource
import com.enzo.livedata_mvvm.retrofit.ResponseHandler
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class PhotoRepositoryTest{
    private val responseHandler = ResponseHandler()
    private lateinit var apiService: ApiService
    private lateinit var repository: PhotoRepository
    private val photoList = arrayListOf(Photo("0","1","First","url1","thumbUrl1"),
        Photo("1","2","Second","url2","thumbUrl2"))
    private val photoListResponse = Resource.success(photoList)




    @Before
    fun setUp() {
        apiService = mock()

        runBlocking {
          //  whenever(apiService.getDatas()).thenThrow(mockException)
            whenever(apiService.getDatas()).thenReturn(photoList)
        }
        repository = PhotoRepository(
            apiService,
            responseHandler
        )
    }

    @Test
    fun `test getPhotoList when nothing going wrong, then list is returned`() =
        runBlocking {
            assertEquals(photoListResponse, repository.getDataList())
        }


}