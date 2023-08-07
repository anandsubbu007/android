package com.subbu.invoice.domain.usecase

import androidx.lifecycle.LiveData
import com.subbu.invoice.data.models.entity.Voucher
import com.subbu.invoice.data.repository.VoucherRepo
import com.subbu.invoice.utils.Result


data class VoucherUseCase(
    val getTransactions: GetAllVouchers,
    val getCustomerByTxt: GetCustomerByTxt,
    val updateVoucher: UpdateVouchers,
    val getVoucher: GetVoucher,
    val deleteVoucher: DeleteVoucher
)

class GetAllVouchers(private val repo: VoucherRepo) {
    suspend operator fun invoke(): Result<LiveData<List<Voucher>>> {
        return try {
            val datas = repo.getAll()
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}

class DeleteVoucher(private val repo: VoucherRepo) {
    suspend operator fun invoke(voucher: Voucher): Result<Unit> {
        return try {
            val datas = repo.delete(voucher)
            Result.Success(datas)
        } catch (e: Exception) {
            println("Error: $e")
            Result.Error(e);
        }
    }
}

class GetVoucher(private val repo: VoucherRepo) {
    suspend operator fun invoke(id: Int): Result<Voucher> {
        return try {
            val datas = repo.get(id)
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}

class UpdateVouchers(private val repo: VoucherRepo) {
    suspend operator fun invoke(data: Voucher): Result<Voucher> {
        return try {
            val datas = repo.upsert(data)
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}