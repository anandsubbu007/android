package com.subbu.invoice.data.models.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.subbu.invoice.data.models.enums.Status

@Entity(tableName = "Items")
data class Item(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    val name: String,
    val price: Float,
    val tax: Float,
    val status: Status,
) : Parcelable {
    @Ignore
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readFloat(),
        Status.Active
    )

    @Ignore
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeFloat(price)
        parcel.writeFloat(tax)
    }

    @Ignore
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        @Ignore
        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }

}

