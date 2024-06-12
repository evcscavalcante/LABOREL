package com.example.laborel.compactação.utils.pdfutils

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class PdfUri(val uri: Uri) : Parcelable {
    constructor(parcel: Parcel) : this(Uri.parse(parcel.readString()!!))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uri.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PdfUri> {
        override fun createFromParcel(parcel: Parcel): PdfUri {
            return PdfUri(parcel)
        }

        override fun newArray(size: Int): Array<PdfUri?> {
            return arrayOfNulls(size)
        }
    }
}
