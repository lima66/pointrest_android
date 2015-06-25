package com.pointrestapp.pointrest.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pointrestapp.pointrest.Constants;
import com.pointrestapp.pointrest.R;
import com.pointrestapp.pointrest.data.PuntiContentProvider;
import com.pointrestapp.pointrest.data.PuntiDbHelper;
import com.pointrestapp.pointrest.data.PuntiImagesDbHelper;

public class ElencoListCursorAdapter extends CursorAdapter {

	public ElencoListCursorAdapter(Context context, Cursor c,
			boolean autoRequery) {
		super(context, c, autoRequery);
		// TODO Auto-generated constructor stub
	}

	private static class ViewHolder {
		public TextView nomePI;
		public ImageView img;
		
		public ViewHolder(View aView) {
			nomePI = (TextView)aView.findViewById(R.id.name_pi);	
			img = (ImageView)aView.findViewById(R.id.pi_image);
		}
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View vView = LayoutInflater.from(context)
				.inflate(R.layout.element_preferiti_screen, parent, false);
		vView.setTag(new ViewHolder(vView));
		return vView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		ViewHolder vHolder = (ViewHolder)view.getTag();	
		
		String namePI = cursor.getString(cursor.getColumnIndex(PuntiDbHelper.NOME));
		vHolder.nomePI.setText(namePI + "");
		
		int preferitoIdColumnIndex = cursor.getColumnIndex(PuntiDbHelper._ID);
		
		Cursor c = context.getContentResolver()
				.query(PuntiContentProvider.PUNTI_IMAGES_URI, 
						new String[]{PuntiImagesDbHelper._ID + ""},
						PuntiImagesDbHelper.PUNTO_ID + "=?",
						new String[]{ cursor.getInt(preferitoIdColumnIndex) + "" },
						null);
		
		if(c.moveToFirst()){
			int imgIdOnRemoteDB = c.getInt(0);
			Glide.with(context).load(Constants.BASE_URL + "immagini/" + imgIdOnRemoteDB).placeholder(R.drawable.ic_place_black_36dp).crossFade().into(vHolder.img);
			
			c.close();
		}
	}
}
