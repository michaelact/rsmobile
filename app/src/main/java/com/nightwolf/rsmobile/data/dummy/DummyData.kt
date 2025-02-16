// data/dummy/DummyData.kt
package com.nightwolf.rsmobile.data.dummy

import com.nightwolf.rsmobile.data.model.Coordinates
import com.nightwolf.rsmobile.data.model.Hospital

object DummyData {
    val hospitals = listOf(
        Hospital(
            name = "RS UMUM DAERAH DR. ZAINOEL ABIDIN",
            address = "JL. TGK DAUD BEUREUEH, NO. 108 B. ACEH",
            region = "KOTA BANDA ACEH, ACEH",
            phone = "(0651) 34565",
            province = "Aceh",
            distance = 2.5,
            coordinates = Coordinates(-8.67522815496819, 115.21163652923)
        ),
        Hospital(
            name = "RSUP SANGLAH",
            address = "Jalan Diponegoro, Denpasar Bali",
            region = "KOTA DENPASAR, BALI",
            phone = "(0361) 227911",
            province = "Bali",
            distance = 3.7,
            coordinates = Coordinates(-8.67522815496819, 115.21163652923)
        ),
        Hospital(
            name = "RS SILOAM DENPASAR",
            address = "Jl. Sunset Road No.818, Kuta, Bali",
            region = "KOTA DENPASAR, BALI",
            phone = "(0361) 779900",
            province = "Bali",
            distance = 5.2,
            coordinates = Coordinates(-8.67522815496819, 115.21163652923)
        )
    )
}
