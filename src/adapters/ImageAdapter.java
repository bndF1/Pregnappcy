package adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.pregnappcy.app.R;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			//imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER);
			//imageView.setPadding(6,6,6,6);
		} else {
			imageView = (ImageView) convertView;
		}
		
		if(mThumbIds[position].equals(String.valueOf(R.drawable.boton_parto))){
			imageView.setScaleType(ImageView.ScaleType.MATRIX);
		}
		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	// references to our images
	private Integer[] mThumbIds = { R.drawable.informacion,
			R.drawable.tienda_online, R.drawable.sintomas, R.drawable.valores,
			R.drawable.calendario, R.drawable.compartir_fotos,
			R.drawable.compara_embarazo, R.drawable.nombres_bebe,

			R.drawable.ajustes, R.drawable.boton_parto,

	};
}
