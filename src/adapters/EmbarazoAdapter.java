package adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pregnappcy.app.R;
import com.pregnappcy.app.database.Embarazo;

public class EmbarazoAdapter extends BaseAdapter {

	Activity context;
	ArrayList<Embarazo> datos;

	public EmbarazoAdapter(Activity context, ArrayList<Embarazo> datos) {
		this.context = context;
		this.datos = datos;
	}

	@Override
	public int getCount() {
		return datos.size();
	}

	@Override
	public Object getItem(int position) {
		return datos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(
				com.pregnappcy.app.R.layout.list_embarazo_items, null);
		Embarazo e = datos.get(position);
		ImageView i = (ImageView) item.findViewById(R.id.SexoBebe);
		i.setImageResource(R.drawable.sexo_bebe);
		TextView embarazoDe = (TextView) item.findViewById(R.id.SemanasEmbarazo);
		
		embarazoDe.setText("18 semanas");
		TextView nombrePadre = (TextView) item
				.findViewById(R.id.NombrePadreMadre);
		nombrePadre.setText(e.getNombre_padre() + " y " + e.getNombre_madre());
		TextView nombreBebe = (TextView) item.findViewById(R.id.NombreBebe);
		if (e.getNombre_bebe().equals("nombre_bebe")){
			nombreBebe.setText("Átodav’a no saben el nombre!");
		}
		else{
			for(String s: e.getNombre_bebe())
			nombreBebe.setText(s);
		}
		TextView informacionRelevante = (TextView) item.findViewById(R.id.InformacionRelevante);
		informacionRelevante.setText("Informaci—n relevante");
		return item;
	}

}
