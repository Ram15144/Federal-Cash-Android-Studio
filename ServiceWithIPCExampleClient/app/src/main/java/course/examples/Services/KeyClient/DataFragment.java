package course.examples.Services.KeyClient;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ram_n on 12/5/2017.
 */

public class DataFragment extends Fragment {

    private static final String TAG = "DataFragment";
    public static final String SAVED_OBJECT_TAG="SavedObjectTag";

    ArrayList<String> datalist = new ArrayList<String>();
    ArrayAdapter<String> dataAdapter;
    ListView data_view;

    private int mCurrIdx = -1;
    private int mDataArrLen;
    private String[] mDataArr;

    int getShownIndex() {
        return mCurrIdx;
    }

    // Show the data in the mDataArr string array at position newIndex by inputting it into the datalist and updating the dataAdapter
    void showDataAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mDataArrLen)
            return;
        mCurrIdx = newIndex;

        String[] result;
        switch(mCurrIdx) {
            case 0:
                    if (mDataArr[0] == null) {
                        datalist.clear();
                        datalist.add("Nothing to show!");
                        dataAdapter.notifyDataSetChanged();
                    }
                    else {
                        result = mDataArr[0].split(" ");
                        datalist.clear();
                        for (int i = 0; i < result.length; i++) {
                            datalist.add(result[i]);
                        }
                        dataAdapter.notifyDataSetChanged();
                    }
                    break;
            case 1:
                    if (mDataArr[1] == null)
                    {
                        datalist.clear();
                        datalist.add("Nothing to show!");
                        dataAdapter.notifyDataSetChanged();
                    }
                    else {
                        result = mDataArr[1].split(" ");
                        datalist.clear();
                        for (int i = 0; i < result.length; i++) {
                            datalist.add(result[i]);
                        }
                        dataAdapter.notifyDataSetChanged();
                    }
                    break;
            case 2: if (mDataArr[2] == null)
                    {
                        datalist.clear();
                        datalist.add("Nothing to show!");
                        dataAdapter.notifyDataSetChanged();
                    }
                    else {
                        datalist.clear();
                        datalist.add(mDataArr[2]);
                        dataAdapter.notifyDataSetChanged();
                    }
                    break;
        }

    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");

        // Inflate the layout defined in landmark_webpage_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.data_fragment_layout,
                container, false);
    }

    // Set up some information about the myWebView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        data_view=(ListView)getActivity().findViewById(R.id.data_view);

        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datalist);
        data_view.setAdapter(dataAdapter);
        mDataArrLen = DisplayData.data.length;
        mDataArr = DisplayData.data;
    }

    @Override
    public void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        outstate.putInt(SAVED_OBJECT_TAG,mCurrIdx);
    }



}
