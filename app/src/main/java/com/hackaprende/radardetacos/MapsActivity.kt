package com.hackaprende.radardetacos

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val taquerias = mutableListOf<Taqueria>()
    private lateinit var tacoIcon: BitmapDescriptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        taquerias.add(Taqueria("Tacos de Asada", -33.954044, 151.241283))
        taquerias.add(Taqueria("Tacos de Pastor", -33.967154, 151.264715))
        taquerias.add(Taqueria("Tacos de Cochinita", -33.9438208,151.2436039))
        taquerias.add(Taqueria("Tacos de Barbacoa", -33.936577, 151.259410))
        taquerias.add(Taqueria("Tacos de Birria", -33.9362557,151.2392932))
        taquerias.add(Taqueria("Burritos", -33.938594, 151.224316))

        tacoIcon = getTacoIcon()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        for (taqueria in taquerias) {
            val tacoPosition = LatLng(taqueria.latitude, taqueria.longitude)
            val tacoMarker = MarkerOptions().icon(tacoIcon).position(tacoPosition).title(taqueria.name)
            mMap.addMarker(tacoMarker)
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(taquerias[0].latitude, taquerias[0].longitude), 13.0f))
    }

    private fun getTacoIcon(): BitmapDescriptor {
        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_taco)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(drawable?.intrinsicWidth ?: 0,
                drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}