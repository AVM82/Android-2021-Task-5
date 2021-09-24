package org.rsschool.android2021task5.repository

import org.rsschool.android2021task5.model.Image

interface Repository {

    suspend fun getImages(): List<Image>?

}
