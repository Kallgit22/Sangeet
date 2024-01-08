package com.agamya.sangeetduniya

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior


class LocalMusic : Fragment(),SongInterface {

    private lateinit var rv:RecyclerView
    private lateinit var songData:ArrayList<SongDataModel>
    private lateinit var dialog:BottomSheetBehavior<CardView>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_local_music, container, false)
        rv = view.findViewById(R.id.Rview)
        rv.layoutManager = LinearLayoutManager(requireActivity())

        dialog = BottomSheetBehavior.from(view.findViewById(R.id.card))
        dialog.peekHeight = 130
        dialog.state = BottomSheetBehavior.STATE_COLLAPSED
        songData = ArrayList()


        getSongData()

        return view
    }

    private fun getSongData(){
        val path = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM)

        val cursor = requireActivity().contentResolver.query(path,projection,selection,null,null)

        if (cursor!=null){
            while (cursor.moveToNext()){
                val song = SongDataModel(cursor.getString(0),
                    cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4))

                songData.add(song)
            }
            cursor.close()
        }
        val adapter = SongAdapter(songData,this)
        rv.adapter = adapter

    }

    override fun onClick(position: Int) {
        val path = songData[position].path
        startSong(path)
        dialog.state  = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun startSong(path:String){
        MainActivity.mediaplayer!!.reset()
        MainActivity.mediaplayer!!.setDataSource(path)
        MainActivity.mediaplayer!!.prepare()
        MainActivity.mediaplayer!!.start()
    }

}