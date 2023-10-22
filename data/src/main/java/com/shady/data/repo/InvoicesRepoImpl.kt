package com.shady.data.repo

import com.shady.data.remote.ApiService
import com.shady.domain.entity.beans.BuildingBean
import com.shady.domain.entity.beans.InvoiceBean
import com.shady.domain.entity.beans.UserBean
import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.entity.responses.UsersResponse
import com.shady.domain.repo.InvoicesRepo

class InvoicesRepoImpl(private val apiService: ApiService) : InvoicesRepo {
    override suspend fun requestInvoices(params: HashMap<String, String>): InvoicesResponse =
        InvoicesResponse(
            mutableListOf(
                InvoiceBean(
                    "فاتورة شهر أكتوبر 2023",
                    "#337234",
                    "39 طلعت حرب رقم الوحدة 019 منطقة وسط البلد",
                    "3500.00"
                ),
                InvoiceBean(
                    "فاتورة شهر نوفمبر 2023",
                    "#337234",
                    "39 طلعت حرب رقم الوحدة 019 منطقة وسط البلد",
                    "3500.00"
                ),
                InvoiceBean(
                    "فاتورة شهر ديسمبر 2023",
                    "#337234",
                    "39 طلعت حرب رقم الوحدة 019 منطقة وسط البلد",
                    "3500.00"
                ),
                InvoiceBean(
                    "فاتورة شهر يناير 2024",
                    "#337234",
                    "39 طلعت حرب رقم الوحدة 019 منطقة وسط البلد",
                    "3500.00"
                ),

            )
        )

    override suspend fun requestUsers(params: HashMap<String, String>): UsersResponse =
        UsersResponse(
            mutableListOf(
                UserBean(
                    "فاتورة شهر أكتوبر 2023",
                    "#337234",
                    "39 طلعت حرب رقم الوحدة 019 منطقة وسط البلد",
                    "654",
                    "0106876789",
                    "3500.00"
                ),
                UserBean(
                    "فاتورة شهر نوفمبر 2023",
                    "#337234",
                    "39 طلعت حرب رقم الوحدة 019 منطقة وسط البلد",
                    "654",
                    "0106876789",
                    "3500.00"
                ),
                UserBean(
                    "فاتورة شهر ديسمبر 2023",
                    "#337234",
                    "39 طلعت حرب رقم الوحدة 019 منطقة وسط البلد",
                    "654",
                    "0106876789",
                    "3500.00"
                ),
                UserBean(
                    "فاتورة شهر يناير 2024",
                    "#337234",
                    "39 طلعت حرب رقم الوحدة 019 منطقة وسط البلد",
                    "654",
                    "0106876789",
                    "3500.00"
                ),

            )
        )

    override suspend fun requestBuildings(): BuildingsResponse = BuildingsResponse(
        mutableListOf(
            BuildingBean("1", "مبنى 1"),
            BuildingBean("2", "مبنى 2"),
            BuildingBean("3", "مبنى 3"),
            BuildingBean("4", "مبنى 4"),
            BuildingBean("5", "مبنى 5"),
            BuildingBean("6", "مبنى 6"),
            BuildingBean("7", "مبنى 7"),
        )
    )

}