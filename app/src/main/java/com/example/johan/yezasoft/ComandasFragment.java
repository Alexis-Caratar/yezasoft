package com.example.johan.yezasoft;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.johan.yezasoft.Adaptadores.AdaptadorComandas;
import com.example.johan.yezasoft.Clases.Comandas;
import com.example.johan.yezasoft.Clases.Url;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComandasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComandasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComandasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ComandasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuPlatosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComandasFragment newInstance(String param1, String param2) {
        ComandasFragment fragment = new ComandasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         SharedPreferences preferences;
        preferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);


        final AsyncHttpClient client = new AsyncHttpClient();
        View view = inflater.inflate(R.layout.fragment_comandasechas, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.lista_comandasechas);

        RequestParams parametros=new RequestParams();
        parametros.put("idempleado",preferences.getString("empleado", ""));
        client.post(Url.url + "mesero/listacomandasechas.php",parametros,new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getMessage().trim(), Toast.LENGTH_SHORT).show();
            }
            //Alex paredes
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("COMANDAS ECHAS", responseString);
                try {
                    JSONArray jsonArray = new JSONArray(responseString);
                    if(jsonArray.length()>0) listView.setAdapter(new AdaptadorComandas(getContext(), client,  Comandas.getArrayList(responseString)));
                    else Toast.makeText(getContext(), "Ningúna Comanda registrada", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "ERROR"+e.getMessage().trim(), Toast.LENGTH_SHORT).show();
                }
            }
        }); return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
