package hoanglv.fpoly.pnlib.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

import hoanglv.fpoly.pnlib.DAO.ThongKeDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.adapters.TopAdapter;
import hoanglv.fpoly.pnlib.models.Top;

public class Top10Fragment extends Fragment {
    ListView lvTop10Sach;
    ArrayList<Top> list;
    TopAdapter topAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10, container, false);
        lvTop10Sach = view.findViewById(R.id.lvTop10Sach);
        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
        list = thongKeDAO.getTop();
        topAdapter = new TopAdapter(requireActivity(), this, list);
        lvTop10Sach.setAdapter(topAdapter);
        return view;
    }
}