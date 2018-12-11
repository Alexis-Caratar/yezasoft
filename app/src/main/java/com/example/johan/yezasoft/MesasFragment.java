package com.example.johan.yezasoft;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.johan.yezasoft.Adaptadores.AdaptadorMenu;
import com.example.johan.yezasoft.Adaptadores.AdaptadorMesas;
import com.example.johan.yezasoft.Clases.Mesas;
import com.example.johan.yezasoft.Clases.Plato;
import com.example.johan.yezasoft.Clases.Url;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MesasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MesasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MesasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MesasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MesasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MesasFragment newInstance(String param1, String param2) {
        MesasFragment fragment = new MesasFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_mesas, container, false);

        //
        TabLayout tabLayoutmesas=(TabLayout) view.findViewById(R.id.tablayoutmesas);
        final ViewPager viewPagermesas=(ViewPager) view.findViewById(R.id.viewpagermesas);
        tabLayoutmesas.addTab(tabLayoutmesas.newTab().setText("DISPONIBLES"),0);
        tabLayoutmesas.addTab(tabLayoutmesas.newTab().setText("OCUPADAS"),1);
        //tabLayoutmesas.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
        tabLayoutmesas.setTabTextColors(Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF"));

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagermesas.setAdapter(adapter);
        viewPagermesas.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutmesas));
        tabLayoutmesas.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagermesas.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0: ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Disponibles");
                        break;
                    case 1: ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Ocupadas");
                        break;
                        default: ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Disponibles");break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        /*final RecyclerView listView =view.findViewById(R.id.lista_mesas);
        final AsyncHttpClient client = new AsyncHttpClient();
        client.post(Url.url + "mesero//listamesas.php", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getMessage().trim(), Toast.LENGTH_SHORT).show();
            }
            //Alex
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("MESAS", responseString);
                try {
                    JSONArray jsonArray = new JSONArray(responseString);
                    ArrayList<Mesas> listaMesas=new ArrayList<>();
                    if(jsonArray.length()>0) {
                        for (int i=0; i<jsonArray.length(); i++){
                            Mesas mesas=new Mesas();
                            mesas.setIdmesa(jsonArray.getJSONObject(i).getString("idmesa"));
                            mesas.setMesainicial(jsonArray.getJSONObject(i).getString("mesainicial"));
                            mesas.setColor(jsonArray.getJSONObject(i).getString("color"));
                            //  Toast.makeText(getContext(), ""+mesas.getColor(), Toast.LENGTH_SHORT).show();
                            listaMesas.add(mesas);
                        }
                        //AdaptadorMesas adapter=new AdaptadorMesas(getContext(),listaMesas);
                        //listView.setLayoutManager(new GridLayoutManager(getContext(),5));
                        //listView.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    Toast.makeText(getContext(), "ERROR"+e.getMessage().trim(), Toast.LENGTH_SHORT).show();
                }
                //responseString

            }
        });*/
        return view;


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
